package pagether.domain.readInfo.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pagether.domain.readInfo.presentation.constant.ResponseMessage.PIN_NOT_ALLOWED;

public class PinNotAllowedException extends ApplicationException {
    public PinNotAllowedException() {
        super(FORBIDDEN.value(), PIN_NOT_ALLOWED.getMessage());
    }
}