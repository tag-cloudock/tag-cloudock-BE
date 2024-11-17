package tagCloudock.domain.tag.exception;

import tagCloudock.global.config.exception.ApplicationException;
import static tagCloudock.domain.tag.presentation.constant.ResponseMessage.STOCK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class StockNotFoundException extends ApplicationException {
    public StockNotFoundException() {
        super(NOT_FOUND.value(), STOCK_NOT_FOUND.getMessage());
    }
}