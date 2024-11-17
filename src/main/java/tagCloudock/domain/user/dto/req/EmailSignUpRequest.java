package tagCloudock.domain.user.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSignUpRequest {
    private String email;
    private String code;
    private String nickname;
    private String accountName;
    private String password;
    private String name;
    private String address;
    private String detailAddress;
    private String phone;
    private String intro;
    private String major;
}
