package pagether.domain.readInfo.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.readInfo.presentation.constant.ResponseMessage.UNAUTHORIZED_ACCESS;

public class UnauthorizedAccessException extends ApplicationException {
    public UnauthorizedAccessException() {
        super(NOT_FOUND.value(), UNAUTHORIZED_ACCESS.getMessage());
    }
}