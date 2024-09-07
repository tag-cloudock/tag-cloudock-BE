package pagether.domain.alert.exception;

import pagether.global.config.exception.ApplicationException;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.alert.presentation.constant.ResponseMessage.ALERT_NOT_FOUND;
import static pagether.domain.news.presentation.constant.ResponseMessage.NEWS_NOT_FOUND;

public class AlertNotFoundException extends ApplicationException {
    public AlertNotFoundException() {
        super(NOT_FOUND.value(), ALERT_NOT_FOUND.getMessage());
    }
}