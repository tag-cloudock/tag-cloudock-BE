package pagether.domain.message.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pagether.domain.chat.domain.ChatRoom;
import pagether.domain.user.domain.User;
import pagether.domain.user.domain.UserType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private ChatRoom chatRoom;

    @Column(nullable = false)
    private UserType userType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
}
