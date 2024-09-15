package pagether.domain.book.domain;

import jakarta.persistence.*;
import lombok.*;
import pagether.domain.note.domain.NoteType;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Long pageCount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String coverImgName;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}