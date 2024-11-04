package gachonherald.domain.comment.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddCommentRequest {
    private String content;
    private Long articleId;
}