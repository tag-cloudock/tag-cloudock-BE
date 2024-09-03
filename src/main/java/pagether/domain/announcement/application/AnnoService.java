package pagether.domain.announcement.application;

import pagether.domain.announcement.domain.Announcement;
import pagether.domain.announcement.dto.req.AddAnnoRequest;
import pagether.domain.announcement.dto.res.AnnoResponse;
import pagether.domain.announcement.exception.AnnoNotFoundException;
import pagether.domain.announcement.repository.AnnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnoService {

    private final AnnoRepository annoRepository;

    public AnnoResponse save(AddAnnoRequest request) {
        Announcement anno = Announcement.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(request.getCreatedAt())
                .build();
        Announcement announcement = annoRepository.save(anno);
        return new AnnoResponse(announcement);
    }

    public AnnoResponse get(Integer annoId) {
        if (!annoRepository.existsById(annoId)) {
            throw new AnnoNotFoundException();
        }
        Announcement anno = annoRepository.findById(annoId).get();
        return new AnnoResponse(anno);
    }

    public List<AnnoResponse> getAll() {
        List<AnnoResponse> annosDTO = new ArrayList<>();
        List<Announcement> annos = annoRepository.findAllByOrderByCreatedAtDesc();

        for (Announcement anno : annos) {
            AnnoResponse dto = new AnnoResponse();
            dto.setAnnoId(anno.getAnnoId());
            dto.setTitle(anno.getTitle());
            dto.setContent(anno.getContent());
            dto.setCreatedAt(anno.getCreatedAt());
            annosDTO.add(dto);
        }
        return annosDTO;
    }

    public void delete(Integer annoId) {
        if (!annoRepository.existsById(annoId)) {
            throw new AnnoNotFoundException();
        }
        annoRepository.deleteById(annoId);
    }
}