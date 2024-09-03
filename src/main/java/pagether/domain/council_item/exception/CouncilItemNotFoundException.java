package pagether.domain.council_item.exception;

import pagether.global.config.exception.ApplicationException;

import static pagether.domain.council_item.presentation.constant.ResponseMessage.COUNCIL_ITEM_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CouncilItemNotFoundException extends ApplicationException {
    public CouncilItemNotFoundException() {
        super(NOT_FOUND.value(), COUNCIL_ITEM_NOT_FOUND.getMessage());
    }
}
