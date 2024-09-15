package pagether.domain.follow.dto.res;

import lombok.*;
import pagether.domain.follow.domain.Follow;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
public class FollowCountResponse {
    private Long followerCount;
    private Long followeeCount;

    @Builder
    public FollowCountResponse(Long followeeCount, Long followerCount) {
        this.followeeCount = followeeCount;
        this.followerCount = followerCount;
    }
}
