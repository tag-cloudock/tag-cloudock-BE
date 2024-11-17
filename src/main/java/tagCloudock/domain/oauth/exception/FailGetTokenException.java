package tagCloudock.domain.oauth.exception;

import tagCloudock.global.config.exception.ApplicationException;

import static tagCloudock.domain.oauth.presentation.constant.ResponseMessage.FAIL_GET_TOKEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailGetTokenException extends ApplicationException {
    public FailGetTokenException() {
        super(INTERNAL_SERVER_ERROR.value(), FAIL_GET_TOKEN.getMessage());
    }
}
