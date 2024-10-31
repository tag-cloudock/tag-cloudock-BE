package gachonherald.domain.block.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static gachonherald.domain.block.presentation.constant.ResponseMessage.USER_BLOCKED;

public class UserBlockedException extends ApplicationException {
    public UserBlockedException() {
        super(FORBIDDEN.value(), USER_BLOCKED.getMessage());
    }
}