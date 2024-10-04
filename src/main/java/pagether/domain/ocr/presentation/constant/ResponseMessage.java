package pagether.domain.ocr.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_EXTRACT("문장 인식을 성공하였습니다."),
    IMAGE_NOT_PROVIDED("이미지가 전달되지 않았습니다."),
    IMAGE_EXTRACT_FAIL("문장 인식을 실패했습니다.")
    ;
    private String message;
}