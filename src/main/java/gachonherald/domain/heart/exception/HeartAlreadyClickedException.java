package gachonherald.domain.heart.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.heart.presentation.constant.ResponseMessage.HEART_ALREADY_CLICKED;

public class HeartAlreadyClickedException extends ApplicationException {
    public HeartAlreadyClickedException() {
        super(NOT_FOUND.value(), HEART_ALREADY_CLICKED.getMessage());
    }
}