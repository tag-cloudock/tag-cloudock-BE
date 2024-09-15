package pagether.domain.follow.domain;

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
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User follower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User followee;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}