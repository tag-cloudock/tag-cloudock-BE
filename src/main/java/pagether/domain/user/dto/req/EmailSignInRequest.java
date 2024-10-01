package pagether.domain.user.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSignInRequest {
    private String email;
    private String accountName;
    private String password;
}
