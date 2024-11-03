package gachonherald.domain.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gachonherald.domain.section.domain.Section;
import gachonherald.domain.user.domain.Role;
import gachonherald.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "articles")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

//    @Lob
    @Column(nullable = false, length = 50000)
    private String content;

    @Column
    private String mainImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User reporter;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "sectionId")
    @JsonIgnore
    private Section section;

    @Column(nullable = false)
    private Long viewCount;

    @Column
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Column
    private Boolean isEditorsPick;

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime publishedAt;
}