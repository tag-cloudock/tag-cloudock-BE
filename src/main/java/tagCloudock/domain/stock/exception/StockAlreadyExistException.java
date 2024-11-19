package tagCloudock.domain.stock.exception;

import tagCloudock.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static tagCloudock.domain.tag.presentation.constant.ResponseMessage.STOCK_NOT_FOUND;

public class StockAlreadyExistException extends ApplicationException {
    public StockAlreadyExistException() {
        super(NOT_FOUND.value(), STOCK_NOT_FOUND.getMessage());
    }
}