package pagether.domain.oauth.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_LOGIN("로그인에 성공했습니다."),
    SUCCESS_REGISTER("회원가입에 성공했습니다."),
    FAIL_GET_TOKEN("카카오 토큰 가져오기에 실패했습니다."),
    ;
    private String message;
}