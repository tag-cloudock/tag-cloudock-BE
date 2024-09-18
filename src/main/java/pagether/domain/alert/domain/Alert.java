package pagether.domain.alert.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pagether.domain.note.domain.Note;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User alarmSender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User alarmReceiver;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertType alertType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Note note;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
