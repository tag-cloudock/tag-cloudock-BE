package pagether.domain.note.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.note.presentation.constant.ResponseMessage.NOTE_NOT_FOUND;

public class NoteNotFountException extends ApplicationException {
    public NoteNotFountException() {
        super(NOT_FOUND.value(), NOTE_NOT_FOUND.getMessage());
    }
}