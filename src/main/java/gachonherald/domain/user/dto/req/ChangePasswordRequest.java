package gachonherald.domain.user.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    private String password;
    private String newPassword;
}
