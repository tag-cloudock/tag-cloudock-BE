package pagether.domain.block.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("생성에 성공했습니다."),
    SUCCESS_READ("조회에 성공했습니다."),
    SUCCESS_UPDATE("수정에 성공했습니다."),
    SUCCESS_DELETE("삭제에 성공했습니다."),
    BLOCK_NOT_FOUND("존재하지 않는 항목입니다."),
    ALREADY_BLOCKED("이미 차단 하고 있습니다."),
    BLOCK_NOT_ALLOWED("차단이 허용되지 않습니다."),
    USER_BLOCKED("상대방이 사용자를 차단하였습니다."),
    ;
    private String message;
}