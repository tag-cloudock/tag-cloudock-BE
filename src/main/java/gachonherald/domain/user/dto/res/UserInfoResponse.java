package gachonherald.domain.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import gachonherald.domain.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String userId;
    private String nickname;
    private String imgPath;
    private String intro;

    public UserInfoResponse(User user,Boolean isFollowed, Boolean isFollowing, Boolean isBlocked, Boolean isBlocking) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
        this.intro = user.getIntro();
    }

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
        this.intro = user.getIntro();
    }
}