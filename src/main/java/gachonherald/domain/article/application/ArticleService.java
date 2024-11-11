package gachonherald.domain.article.application;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import gachonherald.domain.article.dto.ArticleDTO;
import gachonherald.domain.article.dto.SectionDTO;
import gachonherald.domain.article.dto.req.AddArticleRequest;
import gachonherald.domain.article.dto.req.UpdateArticleRequest;
import gachonherald.domain.article.dto.res.ArticleResponse;
import gachonherald.domain.article.dto.res.ArticlesResponse;
import gachonherald.domain.article.dto.res.HomeArticlesResponse;
import gachonherald.domain.article.exception.ArticleNotFoundException;
import gachonherald.domain.article.repository.ArticleRepository;
import gachonherald.domain.section.domain.Section;
import gachonherald.domain.section.domain.SectionStatus;
import gachonherald.domain.section.exception.SectionNotFoundException;
import gachonherald.domain.section.repository.SectionRepository;
import gachonherald.domain.user.domain.Role;
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
public class ArticleService {
    private final UserRepository userRepository;
    private final SectionRepository sectionRepository;
    private final ArticleRepository articleRepository;

    public static final int PAGE_SIZE_LARGE = 10;

    public static final int PAGE_SIZE_SMALL = 3;

    public ArticleResponse save(AddArticleRequest request, String userId) {
        User reporter = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Section section = sectionRepository.findById(request.getSectionId()).orElseThrow(SectionNotFoundException::new);

        Article article = Article.builder()
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .content(request.getContent())
                .reporter(reporter)
                .section(section)
                .status(request.getStatus() == null ? ArticleStatus.EDITING : ArticleStatus.valueOf(request.getStatus()))
                .isEditorsPick(false)
                .viewCount(0L)
                .mainImage(request.getMainImage())
                .publishedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        article = articleRepository.save(article);
        return new ArticleResponse(article);
    }

    public ArticleResponse update(UpdateArticleRequest request) {
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(ArticleNotFoundException::new);
        Section section = sectionRepository.findById(request.getSectionId()).orElseThrow(SectionNotFoundException::new);
        article.setTitle(request.getTitle());
        article.setStatus(ArticleStatus.valueOf(request.getStatus()));
        article.setSubtitle(request.getSubtitle());
        article.setContent(request.getContent());
        article.setSection(section);
        article.setUpdatedAt(LocalDateTime.now());
        article = articleRepository.save(article);
        return new ArticleResponse(article);
    }

    public ArticleResponse getFromReader(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);

        if (article.getStatus().equals(ArticleStatus.ARCHIVED) || article.getStatus().equals(ArticleStatus.PUBLISHED)){
            return new ArticleResponse(article);
        }
        throw new UnauthorizedAccessException();
    }

    public ArticleResponse getFromReporter(Long articleId, String userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        User reporter = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        if (article.getReporter().getUserId().equals(userId) || reporter.getRole().equals(Role.ADMIN)){
            return new ArticleResponse(article);
        }
        throw new UnauthorizedAccessException();
    }


    public ArticlesResponse getArticlesBySection(Long sectionId, int pageNumber) {
        List<ArticleDTO> articlesResponse = new ArrayList<>();
        Section section = sectionRepository.findById(sectionId).orElseThrow(SectionNotFoundException::new);
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE_LARGE);
        List<Article> articles = articleRepository.findAllBySectionAndStatusInOrderByArticleIdDesc(section, List.of(ArticleStatus.PUBLISHED, ArticleStatus.ARCHIVED), pageable);
        int totalCount = articleRepository.countAllBySectionAndStatusIn(section,List.of(ArticleStatus.PUBLISHED, ArticleStatus.ARCHIVED));
        int pageCount = (totalCount-1)/PAGE_SIZE_LARGE+1;
        for(Article article : articles) articlesResponse.add(new ArticleDTO(article));
        return new ArticlesResponse(articlesResponse, pageCount);
    }

    public ArticlesResponse getArticlesByReporter(Long reporterId, int pageNumber) {
        List<ArticleDTO> articlesResponse = new ArrayList<>();
        User reporter = userRepository.findById(reporterId).orElseThrow(UserNotFountException::new);

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE_SMALL);
        List<Article> articles = articleRepository.findAllByReporterAndStatusInOrderByArticleIdDesc(reporter, List.of(ArticleStatus.PUBLISHED, ArticleStatus.ARCHIVED), pageable);
        int totalCount = articleRepository.countAllByReporterAndStatusIn(reporter, List.of(ArticleStatus.PUBLISHED, ArticleStatus.ARCHIVED));
        int pageCount = (totalCount-1)/PAGE_SIZE_SMALL+1;
        for(Article article : articles) articlesResponse.add(new ArticleDTO(article));
        return new ArticlesResponse(articlesResponse, pageCount);
    }

    public ArticlesResponse getUnpublishedArticles(String userId) {
        List<ArticleDTO> articlesResponse = new ArrayList<>();
        User reporter = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Article> articles = articleRepository.findAllByReporterAndStatusNotInOrderByArticleIdDesc(reporter, List.of(ArticleStatus.PUBLISHED, ArticleStatus.ARCHIVED));
        for(Article article : articles) articlesResponse.add(new ArticleDTO(article));
        return new ArticlesResponse(articlesResponse);
    }

    public HomeArticlesResponse getHomeArticles() {
        List<ArticleDTO> editorsPickArticlesResponse = new ArrayList<>();
        List<SectionDTO> sectionsResponse = new ArrayList<>();

        List<Article> editorsPickArticles = articleRepository.findAllByIsEditorsPickOrderBySection_OrderNumber(true);
        for(Article article : editorsPickArticles) editorsPickArticlesResponse.add(new ArticleDTO(article));

        List<Section> sections = sectionRepository.findAllByStatusOrderByOrderNumber(SectionStatus.ACTIVE);

        for(Section section : sections){
            List<ArticleDTO> imageArticleDTOs = new ArrayList<>();
            List<ArticleDTO> articleDTOs = new ArrayList<>();
            List<Article> articles = articleRepository.findAllBySectionAndStatusOrderByArticleIdDesc(section, ArticleStatus.PUBLISHED);
            for(Article article : articles) {
                if (article.getMainImage().isEmpty()) articleDTOs.add(new ArticleDTO(article));
                else imageArticleDTOs.add(new ArticleDTO(article));
            }
            if (imageArticleDTOs.isEmpty() && articleDTOs.isEmpty()) continue;
            sectionsResponse.add(new SectionDTO(section, imageArticleDTOs, articleDTOs));
        }
        return new HomeArticlesResponse(editorsPickArticlesResponse, sectionsResponse);
    }
}