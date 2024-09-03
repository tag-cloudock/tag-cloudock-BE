package pagether.domain.chat.exception;

import pagether.global.config.exception.ApplicationException;
import pagether.domain.chat.presentation.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class FailSendMessageException extends ApplicationException {
    public FailSendMessageException() {
        super(INTERNAL_SERVER_ERROR.value(), ResponseMessage.FAIL_SEND_MESSAGE.getMessage());
    }
}
