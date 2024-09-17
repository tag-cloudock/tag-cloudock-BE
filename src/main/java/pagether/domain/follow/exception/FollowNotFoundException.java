package pagether.domain.follow.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.follow.presentation.constant.ResponseMessage.FOLLOW_NOT_FOUND;

public class FollowNotFoundException extends ApplicationException {
    public FollowNotFoundException() {
        super(NOT_FOUND.value(), FOLLOW_NOT_FOUND.getMessage());
    }
}