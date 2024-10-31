package gachonherald.domain.block.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static gachonherald.domain.block.presentation.constant.ResponseMessage.ALREADY_BLOCKED;

public class AlreadyBlockedException extends ApplicationException {
    public AlreadyBlockedException() {
        super(CONFLICT.value(), ALREADY_BLOCKED.getMessage());
    }
}