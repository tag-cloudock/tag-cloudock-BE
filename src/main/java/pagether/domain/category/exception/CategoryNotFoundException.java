package pagether.domain.category.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.category.presentation.ResponseMessage.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends ApplicationException {
    public CategoryNotFoundException() {
        super(NOT_FOUND.value(), CATEGORY_NOT_FOUND.getMessage());
    }
}