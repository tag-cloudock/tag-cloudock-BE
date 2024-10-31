package gachonherald.domain.readInfo.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.readInfo.presentation.constant.ResponseMessage.READ_INFO_NOT_FOUND;

public class ReadInfoNotFountException extends ApplicationException {
    public ReadInfoNotFountException() {
        super(NOT_FOUND.value(), READ_INFO_NOT_FOUND.getMessage());
    }
}