package pagether.domain.note.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pagether.domain.book.domain.Book;
import pagether.domain.readInfo.domain.ReadInfo;
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
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private ReadInfo readInfo;

    @Column(nullable = false)
    private NoteType type;

    @Column(nullable = false)
    private Long heartCount;

    @Column(nullable = false)
    private Boolean isPrivate;

    @Column(nullable = false)
    private Boolean hasSpoilerRisk;

    private String topic;

    private String sentence;

    private Float rating;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public void incrementHeartCount() {
        this.heartCount++;
    }

    public void decrementHeartCount() {
        this.heartCount--;
    }

}