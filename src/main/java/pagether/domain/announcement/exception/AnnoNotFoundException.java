package pagether.domain.announcement.exception;

import pagether.global.config.exception.ApplicationException;

import static pagether.domain.announcement.presentation.constant.ResponseMessage.ANNO_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class AnnoNotFoundException extends ApplicationException {
    public AnnoNotFoundException() {
        super(NOT_FOUND.value(), ANNO_NOT_FOUND.getMessage());
    }
}