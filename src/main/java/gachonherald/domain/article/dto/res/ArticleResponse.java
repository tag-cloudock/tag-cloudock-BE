package gachonherald.domain.article.dto.res;

import gachonherald.domain.article.domain.Article;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String subtitle;
    private String content;
    private String mainImage;
    private Long reporterId;
    private String reporterName;
    private Long sectionId;
    private String sectionName;
    private LocalDateTime publishedAt;

    @Builder
    public ArticleResponse(Article article) {
        articleId = article.getArticleId();
        title = article.getTitle();
        subtitle = article.getSubtitle();
        content = article.getContent();
        mainImage = article.getMainImage();
        reporterId = article.getReporter().getId();
        reporterName = article.getReporter().getNickName();
        sectionId = article.getSection().getSectionId();
        sectionName = article.getSection().getName();
        publishedAt = article.getPublishedAt();
    }
}
