package gachonherald.domain.section.application;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import gachonherald.domain.article.dto.ArticleDTO;
import gachonherald.domain.article.dto.req.AddArticleRequest;
import gachonherald.domain.article.dto.res.ArticleResponse;
import gachonherald.domain.article.dto.res.ArticlesResponse;
import gachonherald.domain.article.exception.ArticleNotFoundException;
import gachonherald.domain.article.repository.ArticleRepository;
import gachonherald.domain.section.domain.Section;
import gachonherald.domain.section.domain.SectionStatus;
import gachonherald.domain.section.dto.SectionDTO;
import gachonherald.domain.section.dto.req.AddSectionRequest;
import gachonherald.domain.section.dto.res.SectionResponse;
import gachonherald.domain.section.dto.res.SectionsResponse;
import gachonherald.domain.section.exception.SectionNotFoundException;
import gachonherald.domain.section.repository.SectionRepository;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;
import gachonherald.global.config.exception.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SectionService {
    private final SectionRepository sectionRepository;
    public void save(AddSectionRequest request) {
        Section section = Section.builder()
                .name(request.getName())
                .build();
        sectionRepository.save(section);
    }

    public SectionResponse get(Long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(SectionNotFoundException::new);
        return new SectionResponse(section);
    }

    public SectionsResponse getList() {
        List<SectionDTO> activeSectionsResponse = new ArrayList<>();
        List<SectionDTO> inactiveSectionsResponse = new ArrayList<>();
        List<Section> activeSections = sectionRepository.findAllByStatusOrderByOrderNumber(SectionStatus.ACTIVE);
        List<Section> inactiveSections = sectionRepository.findAllByStatusOrderByOrderNumber(SectionStatus.INACTIVE);

        for(Section section : activeSections) activeSectionsResponse.add(new SectionDTO(section));
        for(Section section : inactiveSections) inactiveSectionsResponse.add(new SectionDTO(section));
        return new SectionsResponse(activeSectionsResponse, inactiveSectionsResponse);
    }
}