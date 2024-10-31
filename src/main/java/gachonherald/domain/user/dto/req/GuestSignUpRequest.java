package gachonherald.domain.user.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestSignUpRequest {
    private String nickname;
    private String accountName;
}
