package pagether.domain.user.dto.res;

import pagether.domain.user.domain.Role;
import pagether.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String userId;
    private String nickname;
    private String imgPath;
    private Role roles;
    private String accessToken;
    private String refreshToken;


    public UserResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
        this.roles = user.getRole();
    }
}