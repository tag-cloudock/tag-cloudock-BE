package gachonherald.domain.block.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static gachonherald.domain.block.presentation.constant.ResponseMessage.USER_BLOCKING;

public class UserBlockingException extends ApplicationException {
    public UserBlockingException() {
        super(UNPROCESSABLE_ENTITY.value(), USER_BLOCKING.getMessage());
    }
}