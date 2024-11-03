package gachonherald.domain.article.exception;

import gachonherald.global.config.exception.ApplicationException;

import static gachonherald.domain.article.presentation.constant.ResponseMessage.ARTICLE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ArticleNotFoundException extends ApplicationException {
    public ArticleNotFoundException() {
        super(NOT_FOUND.value(), ARTICLE_NOT_FOUND.getMessage());
    }
}