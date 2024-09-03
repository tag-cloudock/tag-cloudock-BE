package pagether.domain.message.exception;

import pagether.global.config.exception.ApplicationException;

import static pagether.domain.chat.presentation.constant.ResponseMessage.FAIL_SEND_MESSAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class PermissionDeniedException extends ApplicationException {
    public PermissionDeniedException() {
        super(FORBIDDEN.value(), FAIL_SEND_MESSAGE.getMessage());
    }
}
