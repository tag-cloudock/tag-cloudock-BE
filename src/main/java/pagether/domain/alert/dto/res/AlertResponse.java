package pagether.domain.alert.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.news.domain.News;
import lombok.*;
import pagether.domain.note.domain.Note;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlertResponse {
    private Long alertId;
    private User alarmSender;
    private User alarmReceiver;
    private AlertType alertType;
    private Note note;
    private LocalDateTime createdAt;


    @Builder
    public AlertResponse(Alert alert) {
        alertId = alert.getAlertId();
        alarmSender = alert.getAlarmSender();
        alarmReceiver = alert.getAlarmReceiver();
        note = alert.getNote();
        createdAt = alert.getCreatedAt();
    }
}
