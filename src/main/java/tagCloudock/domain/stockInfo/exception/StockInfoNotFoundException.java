package tagCloudock.domain.stockInfo.exception;

import tagCloudock.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static tagCloudock.domain.tag.presentation.constant.ResponseMessage.STOCK_NOT_FOUND;

public class StockInfoNotFoundException extends ApplicationException {
    public StockInfoNotFoundException() {
        super(NOT_FOUND.value(), STOCK_NOT_FOUND.getMessage());
    }
}