package pagether.domain.follow.dto;

import lombok.*;
import pagether.domain.follow.domain.Follow;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


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
