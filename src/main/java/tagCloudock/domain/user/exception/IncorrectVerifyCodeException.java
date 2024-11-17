package tagCloudock.domain.user.exception;

import tagCloudock.global.config.exception.ApplicationException;

import static tagCloudock.domain.user.presentation.constant.ResponseMessage.INCORRECT_VERIFY_CODE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class IncorrectVerifyCodeException extends ApplicationException {
    public IncorrectVerifyCodeException() {
        super(UNAUTHORIZED.value(), INCORRECT_VERIFY_CODE.getMessage());
    }
}