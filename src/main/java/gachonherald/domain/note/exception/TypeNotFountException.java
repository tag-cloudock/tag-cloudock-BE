package gachonherald.domain.note.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.note.presentation.constant.ResponseMessage.TYPE_NOT_FOUND;

public class TypeNotFountException extends ApplicationException {
    public TypeNotFountException() {
        super(NOT_FOUND.value(), TYPE_NOT_FOUND.getMessage());
    }
}