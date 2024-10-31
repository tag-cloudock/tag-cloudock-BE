package gachonherald.global.config.exception;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static gachonherald.global.config.exception.constant.ResponseMessage.LAST_PAGE_REACHED;

public class LastPageReachedException extends ApplicationException {
    public LastPageReachedException() {
        super(NO_CONTENT.value(), LAST_PAGE_REACHED.getMessage());
    }
}