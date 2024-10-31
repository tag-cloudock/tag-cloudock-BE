package gachonherald.domain.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gachonherald.domain.article.domain.Article;
import gachonherald.domain.section.domain.Section;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "comments")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String commentId;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String mainImage;

    @Column(nullable = false)
    private Long reporterId;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "articleId")
    @JsonIgnore
    private Article article;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Boolean isVisible;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}