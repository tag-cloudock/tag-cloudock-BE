package gachonherald.global.config.security.exception;

import gachonherald.global.config.exception.ApplicationException;

import static gachonherald.global.config.security.constant.ResponseMessage.EXPIRED_TOKEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class ExpiredTokenException extends ApplicationException {
    public ExpiredTokenException() {
        super(UNAUTHORIZED.value(), EXPIRED_TOKEN.getMessage());
    }
}
