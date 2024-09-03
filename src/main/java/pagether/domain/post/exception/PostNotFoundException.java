package pagether.domain.post.exception;

import pagether.global.config.exception.ApplicationException;

import static pagether.domain.post.presentation.constant.ResponseMessage.POST_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PostNotFoundException extends ApplicationException {
    public PostNotFoundException() {
        super(NOT_FOUND.value(), POST_NOT_FOUND.getMessage());
    }
}
