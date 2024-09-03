package pagether.domain.certification.exception;

import pagether.global.config.exception.ApplicationException;
import pagether.domain.certification.presentation.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CertifiNotFoundException extends ApplicationException {
    public CertifiNotFoundException() {
        super(NOT_FOUND.value(), ResponseMessage.CERFIFI_NOT_FOUND.getMessage());
    }
}
