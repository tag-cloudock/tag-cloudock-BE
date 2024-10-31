package gachonherald.domain.image.exception;

import gachonherald.domain.image.presentation.constant.ResponseMessage;
import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailImageSaveException extends ApplicationException {
    public FailImageSaveException() {
        super(INTERNAL_SERVER_ERROR.value(), ResponseMessage.NULL_IMAGE.getMessage());
    }
}
