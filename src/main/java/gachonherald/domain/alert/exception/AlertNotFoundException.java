package gachonherald.domain.alert.exception;

import gachonherald.global.config.exception.ApplicationException;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static gachonherald.domain.alert.presentation.constant.ResponseMessage.ALERT_NOT_FOUND;

public class AlertNotFoundException extends ApplicationException {
    public AlertNotFoundException() {
        super(NOT_FOUND.value(), ALERT_NOT_FOUND.getMessage());
    }
}