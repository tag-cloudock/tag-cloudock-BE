package gachonherald.domain.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gachonherald.domain.section.domain.Section;
import gachonherald.domain.user.domain.Role;
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
    private String articleId;

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
    @JsonIgnore
    private Section section;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Boolean isVisible;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}