package tagCloudock.domain.news.exception;

import tagCloudock.global.config.exception.ApplicationException;

import static tagCloudock.domain.news.presentation.constant.ResponseMessage.NEWS_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NewsNotFoundException extends ApplicationException {
    public NewsNotFoundException() {
        super(NOT_FOUND.value(), NEWS_NOT_FOUND.getMessage());
    }
}