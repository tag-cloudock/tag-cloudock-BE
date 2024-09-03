package pagether.global.config.security.exception;

import pagether.global.config.exception.ApplicationException;
import pagether.global.config.security.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(UNAUTHORIZED.value(), ResponseMessage.INVALID_TOKEN.getMessage());
    }
}
