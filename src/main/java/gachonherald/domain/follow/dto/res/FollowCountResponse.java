package gachonherald.domain.follow.dto.res;

import lombok.*;


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
