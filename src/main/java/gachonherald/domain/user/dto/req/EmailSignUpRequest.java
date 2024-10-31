package gachonherald.domain.user.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSignUpRequest {
    private String email;
    private String nickname;
    private String accountName;
    private String password;
}
