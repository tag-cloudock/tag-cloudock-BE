package gachonherald.domain.article.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class UpdateArticleRequest {
    private Long articleId;
    private String title;
    private String subtitle;
    private String content;
    private Long sectionId;
}