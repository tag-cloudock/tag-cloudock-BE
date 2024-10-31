package gachonherald.domain.note.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.note.presentation.constant.ResponseMessage.NOTE_NOT_FOUND;

public class NoteNotFountException extends ApplicationException {
    public NoteNotFountException() {
        super(NOT_FOUND.value(), NOTE_NOT_FOUND.getMessage());
    }
}