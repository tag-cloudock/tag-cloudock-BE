package pagether.domain.heart.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.heart.presentation.constant.ResponseMessage.HEART_NOT_FOUND;

public class HeartNotFoundException extends ApplicationException {
    public HeartNotFoundException() {
        super(NOT_FOUND.value(), HEART_NOT_FOUND.getMessage());
    }
}