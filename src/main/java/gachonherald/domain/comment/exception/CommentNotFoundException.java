package gachonherald.domain.comment.exception;

import gachonherald.global.config.exception.ApplicationException;

import static gachonherald.domain.article.presentation.constant.ResponseMessage.ARTICLE_NOT_FOUND;
import static gachonherald.domain.comment.presentation.constant.ResponseMessage.COMMENT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CommentNotFoundException extends ApplicationException {
    public CommentNotFoundException() {
        super(NOT_FOUND.value(), COMMENT_NOT_FOUND.getMessage());
    }
}