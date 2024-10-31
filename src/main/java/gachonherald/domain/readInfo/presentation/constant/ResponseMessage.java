package gachonherald.domain.readInfo.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("생성에 성공했습니다."),
    SUCCESS_READ("조회에 성공했습니다."),
    SUCCESS_UPDATE("수정에 성공했습니다."),
    SUCCESS_DELETE("삭제에 성공했습니다."),
    READ_INFO_NOT_FOUND("존재하지 않는 항목입니다."),
    UNAUTHORIZED_ACCESS("허가 받지 않은 접근 입니다."),
    PIN_NOT_ALLOWED("핀이 허용되지 않습니다."),
    ;
    private String message;
}