package pagether.domain.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pagether.domain.category.domain.Category;
import pagether.domain.note.domain.NoteType;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Book {
    @Id
    private String isbn;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Category category;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}