package gachonherald.domain.follow.dto.res;

import lombok.*;
import gachonherald.domain.follow.domain.Follow;
import gachonherald.domain.user.domain.User;

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
