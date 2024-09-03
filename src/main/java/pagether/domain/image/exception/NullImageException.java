package pagether.domain.image.exception;

import pagether.global.config.exception.ApplicationException;
import pagether.domain.certification.presentation.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NullImageException extends ApplicationException {
    public NullImageException() {
        super(NOT_FOUND.value(), ResponseMessage.NULL_IMAGE.getMessage());
    }
}
