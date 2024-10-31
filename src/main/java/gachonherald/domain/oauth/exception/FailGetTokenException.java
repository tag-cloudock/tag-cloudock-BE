package gachonherald.domain.oauth.exception;

import gachonherald.global.config.exception.ApplicationException;

import static gachonherald.domain.oauth.presentation.constant.ResponseMessage.FAIL_GET_TOKEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailGetTokenException extends ApplicationException {
    public FailGetTokenException() {
        super(INTERNAL_SERVER_ERROR.value(), FAIL_GET_TOKEN.getMessage());
    }
}
