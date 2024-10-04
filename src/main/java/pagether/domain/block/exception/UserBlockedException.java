package pagether.domain.block.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pagether.domain.block.presentation.constant.ResponseMessage.USER_BLOCKED;

public class UserBlockedException extends ApplicationException {
    public UserBlockedException() {
        super(FORBIDDEN.value(), USER_BLOCKED.getMessage());
    }
}