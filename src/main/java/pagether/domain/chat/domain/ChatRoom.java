package pagether.domain.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pagether.domain.post.domain.Post;
import pagether.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(unique = true, nullable = false)
    private String roomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User borrower;

    @Column
    private Long borrowerSessionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User lender;

    @Column
    private Long lenderSessionId;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}