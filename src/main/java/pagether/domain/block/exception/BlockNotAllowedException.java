package pagether.domain.block.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.block.presentation.constant.ResponseMessage.BLOCK_NOT_ALLOWED;

public class BlockNotAllowedException extends ApplicationException {
    public BlockNotAllowedException() {
        super(NOT_FOUND.value(), BLOCK_NOT_ALLOWED.getMessage());
    }
}