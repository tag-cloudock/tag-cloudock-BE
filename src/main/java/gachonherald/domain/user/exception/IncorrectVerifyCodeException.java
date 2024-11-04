package gachonherald.domain.user.exception;

import gachonherald.global.config.exception.ApplicationException;

import static gachonherald.domain.user.presentation.constant.ResponseMessage.INCORRECT_PASSWORD;
import static gachonherald.domain.user.presentation.constant.ResponseMessage.INCORRECT_VERIFY_CODE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class IncorrectVerifyCodeException extends ApplicationException {
    public IncorrectVerifyCodeException() {
        super(UNAUTHORIZED.value(), INCORRECT_VERIFY_CODE.getMessage());
    }
}