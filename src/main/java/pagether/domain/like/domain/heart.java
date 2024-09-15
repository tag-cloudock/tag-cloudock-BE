package pagether.domain.like.domain;

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
public class heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Note note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User reporter;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}