package gachonherald.domain.article.dto;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ArticleDTO {
    private Long articleId;
    private Long sectionId;
    private String sectionName;
    private String title;
    private String subtitle;
    private ArticleStatus status;
    private String mainImage;
    private String mainImageSource;
    private Long reporterId;
    private String reporterName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;


    public ArticleDTO(Article article) {
        sectionId = article.getSection().getSectionId();
        sectionName = article.getSection().getName();
        articleId = article.getArticleId();
        title = article.getTitle();
        subtitle = article.getSubtitle();
        status = article.getStatus();
        mainImage = article.getMainImage();
        mainImageSource = "hello";
        reporterId = article.getReporter().getId();
        reporterName = article.getReporter().getNickName();
        createdAt = article.getCreatedAt();
        updatedAt = article.getUpdatedAt();
        publishedAt = article.getPublishedAt();
    }
}
