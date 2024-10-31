package gachonherald.domain.ocr.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.*;
import static gachonherald.domain.ocr.presentation.constant.ResponseMessage.IMAGE_EXTRACT_FAIL;

public class ErrorDetectedException extends ApplicationException {
    public ErrorDetectedException() {
        super(UNPROCESSABLE_ENTITY.value(), IMAGE_EXTRACT_FAIL.getMessage());
    }
}