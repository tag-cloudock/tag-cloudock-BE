package pagether.domain.user.dto.req;

import pagether.domain.user.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Long id;

    private String userid;

    private String nickname;

    private String password;

    private Role role;
}
