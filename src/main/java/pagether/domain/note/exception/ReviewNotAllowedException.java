package pagether.domain.note.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.note.presentation.constant.ResponseMessage.REVIEW_NOT_ALLOWED;

public class ReviewNotAllowedException extends ApplicationException {
    public ReviewNotAllowedException() {
        super(NOT_FOUND.value(), REVIEW_NOT_ALLOWED.getMessage());
    }
}