package gachonherald.global.config.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    UNAUTHORIZED_ACCESS("허가 받지 않은 접근 입니다."),
    ILLEGAL_ARGUMENT("잘못된 인자 입니다."),
    LAST_PAGE_REACHED("더 이상 로드할 데이터가 없습니다."),
    ;
    private String message;
}