package gachonherald.domain.section.exception;

import gachonherald.global.config.exception.ApplicationException;

import static gachonherald.domain.section.presentation.ResponseMessage.SECTION_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class SectionNotFoundException extends ApplicationException {
    public SectionNotFoundException() {
        super(NOT_FOUND.value(), SECTION_NOT_FOUND.getMessage());
    }
}