package gachonherald.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private Boolean isAccountPrivate;

    @Column
    private String imgPath = "default.png";

    @Column
    private String passWord;

    @Column(nullable = false)
    private Long lastSeenNewsId;

    @Column(nullable = false)
    private Long lastSeenAlertId;

    @Column(nullable = false)
    private Boolean isHeartAlertEnabled;

    @Column(nullable = false)
    private Boolean isFollowAlertEnabled;

    @Column(nullable = false)
    private Boolean isCommentAlertEnabled;

    @Enumerated(EnumType.STRING)
    private Role role;
}