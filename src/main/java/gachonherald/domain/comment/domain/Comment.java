package gachonherald.domain.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gachonherald.domain.article.domain.Article;
import gachonherald.domain.section.domain.Section;
import gachonherald.domain.user.domain.User;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User commenter;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Article article;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}