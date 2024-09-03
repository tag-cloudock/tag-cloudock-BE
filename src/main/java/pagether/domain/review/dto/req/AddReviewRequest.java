package pagether.domain.review.dto.req;

import pagether.domain.review.domain.RateType;
import pagether.domain.user.domain.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddReviewRequest {
    private Integer postId;
    private UserType writerType;
    private String recipientId;
    private RateType rate;
    private String text;
}
