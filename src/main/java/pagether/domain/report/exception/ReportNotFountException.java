package pagether.domain.report.exception;

import pagether.global.config.exception.ApplicationException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pagether.domain.report.presentation.constant.ResponseMessage.REPORT_NOT_FOUND;

public class ReportNotFountException extends ApplicationException {
    public ReportNotFountException() {
        super(NOT_FOUND.value(), REPORT_NOT_FOUND.getMessage());
    }
}