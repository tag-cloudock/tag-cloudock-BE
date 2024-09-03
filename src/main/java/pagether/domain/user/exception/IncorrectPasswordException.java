package pagether.domain.user.exception;

import pagether.global.config.exception.ApplicationException;

import static pagether.domain.user.presentation.constant.ResponseMessage.INCORRECT_PASSWORD;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class IncorrectPasswordException extends ApplicationException {
    public IncorrectPasswordException() {
        super(UNAUTHORIZED.value(), INCORRECT_PASSWORD.getMessage());
    }
}