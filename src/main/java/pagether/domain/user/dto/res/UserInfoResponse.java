package pagether.domain.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pagether.domain.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private String id;
    private String userId;
    private String nickname;
    private String imgPath;
    private String bio;
    private Boolean isAccountPrivate;
    private Boolean isFollowed;
    private Boolean isFollowing;
    private Boolean isBlocked;
    private Boolean isBlocking;

    public UserInfoResponse(User user,Boolean isFollowed, Boolean isFollowing, Boolean isBlocked, Boolean isBlocking) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
        this.bio = user.getBio();
        this.isAccountPrivate = user.getIsAccountPrivate();
        this.isFollowed = isFollowed;
        this.isFollowing = isFollowing;
        this.isBlocked = isBlocked;
        this.isBlocking = isBlocking;
    }

    public UserInfoResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
        this.bio = user.getBio();
    }
}