package pagether.domain.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pagether.domain.user.domain.Role;
import pagether.domain.user.domain.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponse {

    private String id;
    private String userId;
    private String nickname;
    private String imgPath;

    public UserSearchResponse(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
    }
}