package tagCloudock.domain.image.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_CREATE("이미지 저장을 성공하였습니다."),
    NULL_IMAGE("이미지가 없습니다."),
    IMAGE_NOT_FOUND("존재하지 않는 항목입니다."),
    ;
    private String message;
}