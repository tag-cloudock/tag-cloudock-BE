package pagether.domain.image.exception;

import pagether.domain.image.presentation.constant.ResponseMessage;
import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailImageSaveException extends ApplicationException {
    public FailImageSaveException() {
        super(INTERNAL_SERVER_ERROR.value(), ResponseMessage.NULL_IMAGE.getMessage());
    }
}
