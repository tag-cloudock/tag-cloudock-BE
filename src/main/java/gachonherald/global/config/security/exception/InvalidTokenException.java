package gachonherald.global.config.security.exception;

import gachonherald.global.config.exception.ApplicationException;
import gachonherald.global.config.security.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(UNAUTHORIZED.value(), ResponseMessage.INVALID_TOKEN.getMessage());
    }
}
