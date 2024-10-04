package pagether.domain.ocr.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.*;
import static pagether.domain.ocr.presentation.constant.ResponseMessage.IMAGE_EXTRACT_FAIL;
import static pagether.domain.ocr.presentation.constant.ResponseMessage.IMAGE_NOT_PROVIDED;

public class ErrorDetectedException extends ApplicationException {
    public ErrorDetectedException() {
        super(UNPROCESSABLE_ENTITY.value(), IMAGE_EXTRACT_FAIL.getMessage());
    }
}