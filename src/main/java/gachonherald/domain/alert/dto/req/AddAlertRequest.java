package gachonherald.domain.alert.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddAlertRequest {
    private String alarmSender;
    private String alarmReceiver;
    private String content;
}