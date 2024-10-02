package pagether.domain.follow.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static pagether.domain.follow.presentation.constant.ResponseMessage.ALREADY_FOLLOWED;

public class AlreadyFollowedException extends ApplicationException {
    public AlreadyFollowedException() {
        super(CONFLICT.value(), ALREADY_FOLLOWED.getMessage());
    }
}