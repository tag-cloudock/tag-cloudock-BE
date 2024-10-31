package gachonherald.domain.note.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.note.presentation.constant.ResponseMessage.REVIEW_NOT_ALLOWED;

public class ReviewNotAllowedException extends ApplicationException {
    public ReviewNotAllowedException() {
        super(NOT_FOUND.value(), REVIEW_NOT_ALLOWED.getMessage());
    }
}