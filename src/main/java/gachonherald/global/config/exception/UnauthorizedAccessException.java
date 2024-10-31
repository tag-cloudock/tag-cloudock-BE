package gachonherald.global.config.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.global.config.exception.constant.ResponseMessage.UNAUTHORIZED_ACCESS;

public class UnauthorizedAccessException extends ApplicationException {
    public UnauthorizedAccessException() {
        super(NOT_FOUND.value(), UNAUTHORIZED_ACCESS.getMessage());
    }
}