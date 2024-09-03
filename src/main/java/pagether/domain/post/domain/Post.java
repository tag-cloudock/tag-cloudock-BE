package pagether.domain.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pagether.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String locationDetail;

    @Column(nullable = false)
    private long rentalFee;

    @Column(nullable = false)
    private String security;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDate needAt;

    @Column(nullable = false)
    private LocalDate returnAt;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isClose;

    @Column(nullable = false)
    private Boolean isLenderWriteReview;

    @Column
    private String lenderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String imgPath;

}