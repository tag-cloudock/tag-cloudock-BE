package pagether.domain.follow.dto.res;

import lombok.*;
import pagether.domain.follow.domain.Follow;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FollowListResponse {
    private List<Follow> followerList;
    private List<Follow> followeeList;

    @Builder
    public FollowListResponse(List<Follow> followeeList, List<Follow> followerList) {
        this.followeeList = followeeList;
        this.followerList = followerList;
    }
}
