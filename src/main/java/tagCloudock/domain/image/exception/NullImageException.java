package tagCloudock.domain.image.exception;

import tagCloudock.domain.image.presentation.constant.ResponseMessage;
import tagCloudock.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NullImageException extends ApplicationException {
    public NullImageException() {
        super(NOT_FOUND.value(), ResponseMessage.NULL_IMAGE.getMessage());
    }
}
