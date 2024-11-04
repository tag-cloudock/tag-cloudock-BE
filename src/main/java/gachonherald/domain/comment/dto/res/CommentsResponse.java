package gachonherald.domain.comment.dto.res;

import gachonherald.domain.comment.dto.CommentDTO;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class CommentsResponse {
    private List<CommentDTO> comments;
    private int pageCount;

    @Builder
    public CommentsResponse(List<CommentDTO> comments, int pageCount) {
        this.comments = comments;
        this.pageCount = pageCount;
    }
}
