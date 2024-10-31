package gachonherald.domain.user.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_LOGIN("로그인에 성공했습니다."),
    SUCCESS_REGISTER("회원가입에 성공했습니다."),
    SUCCESS_READ("조회에 성공했습니다."),
    SUCCESS_UPDATE("수정에 성공했습니다."),
    SUCCESS_DELETE("삭제에 성공했습니다."),
    SUCCESS_REFRESH("토큰 재생성에 성공했습니다."),
    USER_NOT_FOUND("존재하지 않는 유저입니다."),
    DUPLICATE_USER_ID("중복된 유저아이디입니다."),
    INCORRECT_PASSWORD("비밀번호가 틀립니다."),
    ;
    private String message;
}