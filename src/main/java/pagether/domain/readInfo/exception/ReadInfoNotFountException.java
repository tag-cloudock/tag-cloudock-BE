package pagether.domain.readInfo.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.readInfo.presentation.constant.ResponseMessage.READ_INFO_NOT_FOUND;

public class ReadInfoNotFountException extends ApplicationException {
    public ReadInfoNotFountException() {
        super(NOT_FOUND.value(), READ_INFO_NOT_FOUND.getMessage());
    }
}