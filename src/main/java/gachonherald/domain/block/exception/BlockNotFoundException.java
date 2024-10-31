package gachonherald.domain.block.exception;

import gachonherald.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.block.presentation.constant.ResponseMessage.BLOCK_NOT_FOUND;

public class BlockNotFoundException extends ApplicationException {
    public BlockNotFoundException() {
        super(NOT_FOUND.value(), BLOCK_NOT_FOUND.getMessage());
    }
}