package gachonherald.domain.news.exception;

import gachonherald.global.config.exception.ApplicationException;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.news.presentation.constant.ResponseMessage.NEWS_NOT_FOUND;

public class NewsNotFoundException extends ApplicationException {
    public NewsNotFoundException() {
        super(NOT_FOUND.value(), NEWS_NOT_FOUND.getMessage());
    }
}