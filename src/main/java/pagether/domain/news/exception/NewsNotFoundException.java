package pagether.domain.news.exception;

import pagether.global.config.exception.ApplicationException;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.news.presentation.constant.ResponseMessage.NEWS_NOT_FOUND;

public class NewsNotFoundException extends ApplicationException {
    public NewsNotFoundException() {
        super(NOT_FOUND.value(), NEWS_NOT_FOUND.getMessage());
    }
}