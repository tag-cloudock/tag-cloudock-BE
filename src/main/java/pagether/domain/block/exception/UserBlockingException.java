package pagether.domain.block.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static pagether.domain.block.presentation.constant.ResponseMessage.USER_BLOCKED;
import static pagether.domain.block.presentation.constant.ResponseMessage.USER_BLOCKING;

public class UserBlockingException extends ApplicationException {
    public UserBlockingException() {
        super(UNPROCESSABLE_ENTITY.value(), USER_BLOCKING.getMessage());
    }
}