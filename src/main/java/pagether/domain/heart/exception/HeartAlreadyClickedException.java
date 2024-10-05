package pagether.domain.heart.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.heart.presentation.constant.ResponseMessage.HEART_ALREADY_CLICKED;

public class HeartAlreadyClickedException extends ApplicationException {
    public HeartAlreadyClickedException() {
        super(NOT_FOUND.value(), HEART_ALREADY_CLICKED.getMessage());
    }
}