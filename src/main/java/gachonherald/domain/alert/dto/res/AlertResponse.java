package gachonherald.domain.alert.dto.res;

import gachonherald.domain.alert.domain.Alert;
import gachonherald.domain.alert.domain.AlertType;
import lombok.*;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.user.domain.User;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlertResponse {
    private Long alertId;
    private User alarmSender;
    private AlertType alertType;
    private Note note;
    private LocalDateTime createdAt;


    @Builder
    public AlertResponse(Alert alert) {
        alertId = alert.getAlertId();
        alarmSender = alert.getAlarmSender();
        note = alert.getNote();
        createdAt = alert.getCreatedAt();
    }
}
