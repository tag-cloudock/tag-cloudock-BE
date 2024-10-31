package gachonherald.domain.oauth.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoSignUpRequest {
    private String code;
    private String name;
    private String nickname;
    private String userId;
    private String email;
    private String address;
    private String detailAddress;
    private String phone;
    private String intro;
    private String password;

}
