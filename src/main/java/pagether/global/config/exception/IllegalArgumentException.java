package pagether.global.config.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.global.config.exception.constant.ResponseMessage.ILLEGAL_ARGUMENT;

public class IllegalArgumentException extends ApplicationException {
    public IllegalArgumentException() {
        super(NOT_FOUND.value(), ILLEGAL_ARGUMENT.getMessage());
    }
}