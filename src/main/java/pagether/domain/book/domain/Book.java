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

    private String author;
    private String publisher;

    @Column(nullable = false)
    private Long pageCount;

    private String description;
    private String coverImgName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Category category;
    private Integer weight;
    private Integer sizeDepth;
    private Integer sizeHeight;
    private Integer sizeWidth;

    @Column(nullable = false)
    private Boolean isBookAddedDirectly;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}