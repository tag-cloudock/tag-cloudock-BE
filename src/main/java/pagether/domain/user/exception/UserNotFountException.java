package pagether.domain.user.exception;

import pagether.global.config.exception.ApplicationException;

import static pagether.domain.user.presentation.constant.ResponseMessage.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFountException extends ApplicationException {
    public UserNotFountException() {
        super(NOT_FOUND.value(), USER_NOT_FOUND.getMessage());
    }
}