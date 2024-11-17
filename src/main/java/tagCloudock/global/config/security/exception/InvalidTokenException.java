package tagCloudock.global.config.security.exception;

import tagCloudock.global.config.exception.ApplicationException;
import tagCloudock.global.config.security.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(UNAUTHORIZED.value(), ResponseMessage.INVALID_TOKEN.getMessage());
    }
}
