package gachonherald.domain.comment.dto.res;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.comment.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {
    private Long commentId;

    @Builder
    public CommentResponse(Comment comment) {
        commentId = comment.getCommentId();
    }
}
