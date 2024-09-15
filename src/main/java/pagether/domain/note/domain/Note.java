package pagether.domain.note.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pagether.domain.book.domain.Book;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;

    @Column(nullable = false)
    private NoteType type;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Boolean isPrivate;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}