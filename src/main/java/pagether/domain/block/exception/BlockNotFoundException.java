package pagether.domain.block.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.block.presentation.constant.ResponseMessage.BLOCK_NOT_FOUND;
import static pagether.domain.follow.presentation.constant.ResponseMessage.FOLLOW_NOT_FOUND;

public class BlockNotFoundException extends ApplicationException {
    public BlockNotFoundException() {
        super(NOT_FOUND.value(), BLOCK_NOT_FOUND.getMessage());
    }
}