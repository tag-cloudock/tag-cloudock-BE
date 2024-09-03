package pagether.domain.image.exception;

import pagether.global.config.exception.ApplicationException;
import pagether.domain.certification.presentation.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailImageSaveException extends ApplicationException {
    public FailImageSaveException() {
        super(INTERNAL_SERVER_ERROR.value(), ResponseMessage.NULL_IMAGE.getMessage());
    }
}
