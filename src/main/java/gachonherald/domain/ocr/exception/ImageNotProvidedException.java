package gachonherald.domain.ocr.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static gachonherald.domain.ocr.presentation.constant.ResponseMessage.IMAGE_NOT_PROVIDED;

public class ImageNotProvidedException extends ApplicationException {
    public ImageNotProvidedException() {
        super(BAD_REQUEST.value(), IMAGE_NOT_PROVIDED.getMessage());
    }
}