package gachonherald.domain.alert.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import gachonherald.domain.follow.domain.Follow;
import gachonherald.domain.follow.domain.RequestStatus;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.user.domain.User;

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

    private RequestStatus requestStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Note note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Follow follow;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
