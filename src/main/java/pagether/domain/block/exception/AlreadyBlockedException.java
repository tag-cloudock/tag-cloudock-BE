package pagether.domain.block.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static pagether.domain.block.presentation.constant.ResponseMessage.ALREADY_BLOCKED;
import static pagether.domain.follow.presentation.constant.ResponseMessage.ALREADY_FOLLOWED;

public class AlreadyBlockedException extends ApplicationException {
    public AlreadyBlockedException() {
        super(CONFLICT.value(), ALREADY_BLOCKED.getMessage());
    }
}