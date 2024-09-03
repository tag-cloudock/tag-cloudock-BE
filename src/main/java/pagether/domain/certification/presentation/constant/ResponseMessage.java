package pagether.domain.certification.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("생성에 성공했습니다."),
    SUCCESS_READ("조회에 성공했습니다."),
    SUCCESS_UPDATE("수정에 성공했습니다."),
    SUCCESS_DELETE("삭제에 성공했습니다."),
    CERFIFI_NOT_FOUND("인증 요청 정보가 없습니다."),
    NULL_IMAGE("이미지 데이터가 없습니다."),
    FAIL_IMAGE_SAVE("이미지 저장에 실패 했습니다."),
    ;
    private String message;
}