package pagether.domain.council.exception;

import pagether.global.config.exception.ApplicationException;
import pagether.domain.council.presentation.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CouncilNotFoundException extends ApplicationException {
    public CouncilNotFoundException() {
        super(NOT_FOUND.value(), ResponseMessage.COUNCIL_NOT_FOUND.getMessage());
    }
}
