package pagether.domain.ocr.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.note.presentation.constant.ResponseMessage.NOTE_NOT_FOUND;
import static pagether.domain.ocr.presentation.constant.ResponseMessage.IMAGE_NOT_PROVIDED;

public class ImageNotProvidedException extends ApplicationException {
    public ImageNotProvidedException() {
        super(BAD_REQUEST.value(), IMAGE_NOT_PROVIDED.getMessage());
    }
}