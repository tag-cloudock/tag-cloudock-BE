package tagCloudock.domain.user.exception;

import tagCloudock.global.config.exception.ApplicationException;

import static tagCloudock.domain.user.presentation.constant.ResponseMessage.INCORRECT_PASSWORD;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class IncorrectPasswordException extends ApplicationException {
    public IncorrectPasswordException() {
        super(UNAUTHORIZED.value(), INCORRECT_PASSWORD.getMessage());
    }
}