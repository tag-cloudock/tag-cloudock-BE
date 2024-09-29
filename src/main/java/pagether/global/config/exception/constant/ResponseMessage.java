package pagether.global.config.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    UNAUTHORIZED_ACCESS("허가 받지 않은 접근 입니다."),
    ;
    private String message;
}