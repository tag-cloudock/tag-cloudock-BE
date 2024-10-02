package pagether.domain.heart.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.heart.presentation.constant.ResponseMessage.ALREADY_CLICKED_AND_NOT_CLICKED;
public class AlreadyClickedException extends ApplicationException {
    public AlreadyClickedException() {
        super(NOT_FOUND.value(), ALREADY_CLICKED_AND_NOT_CLICKED.getMessage());
    }
}