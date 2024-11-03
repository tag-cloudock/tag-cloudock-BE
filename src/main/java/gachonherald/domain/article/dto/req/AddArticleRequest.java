package gachonherald.domain.article.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddArticleRequest {
    private String title;
    private String subtitle;
    private String content;
    private String mainImage;
    private Long reporterId;
    private Long sectionId;
}