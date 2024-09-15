package pagether.domain.follow.dto.res;

import lombok.*;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.follow.domain.Follow;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowResponse {
    private Long followId;
    private User follower;
    private User followee;
    private LocalDateTime createdAt;

    @Builder
    public FollowResponse(Follow follow) {
        followId = follow.getFollowId();
        followee = follow.getFollowee();
        follower = follow.getFollower();
        createdAt = follow.getCreatedAt();
    }
}
