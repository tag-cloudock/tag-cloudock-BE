package pagether.domain.alert.dto.res;

import lombok.*;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.follow.domain.RequestStatus;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestAlertResponse {
    private Long alertId;
    private User alarmSender;
    private User alarmReceiver;
    private RequestStatus requestStatus;
    private AlertType alertType;
    private LocalDateTime createdAt;


    @Builder
    public RequestAlertResponse(Alert alert) {
        alertId = alert.getAlertId();
        requestStatus = alert.getFollow().getRequestStatus();
        alarmSender = alert.getAlarmSender();
        alertType = AlertType.FOLLOW_REQUEST;
        createdAt = alert.getCreatedAt();
    }
}
