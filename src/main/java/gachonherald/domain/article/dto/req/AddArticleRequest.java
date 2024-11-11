package gachonherald.domain.article.dto.req;

import gachonherald.domain.article.domain.ArticleStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddArticleRequest {
    private String status;
    private String title;
    private String subtitle;
    private String content;
    private String mainImage;
    private Long sectionId;
}