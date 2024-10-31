package gachonherald.domain.follow.dto;

import lombok.*;
import gachonherald.domain.follow.domain.Follow;
import gachonherald.domain.user.domain.User;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowDTO {
    private Long followId;
    private String userId;
    private String userNickName;
    private String userAccountName;
    private String userProfileImgName;

    @Builder
    public FollowDTO(Follow follow, User user) {
        followId = follow.getFollowId();
        userId = user.getUserId();
        userNickName = user.getNickName();
        userAccountName = user.getAccountName();
        userProfileImgName = user.getImgPath();
    }
}
