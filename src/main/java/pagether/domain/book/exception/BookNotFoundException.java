package pagether.domain.book.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.book.presentation.constant.ResponseMessage.BOOK_NOT_FOUND;
public class BookNotFoundException extends ApplicationException {
    public BookNotFoundException() {
        super(NOT_FOUND.value(), BOOK_NOT_FOUND.getMessage());
    }
}