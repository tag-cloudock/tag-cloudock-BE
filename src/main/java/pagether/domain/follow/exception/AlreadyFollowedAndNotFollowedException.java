package pagether.domain.follow.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.follow.presentation.constant.ResponseMessage.ALREADY_FOLLOWED_AND_NOT_FOLLOWED;

public class AlreadyFollowedAndNotFollowedException extends ApplicationException {
    public AlreadyFollowedAndNotFollowedException() {
        super(NOT_FOUND.value(), ALREADY_FOLLOWED_AND_NOT_FOLLOWED.getMessage());
    }
}