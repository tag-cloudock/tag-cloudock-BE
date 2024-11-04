package gachonherald.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String detailAddress;

    @Column
    private String phone;
    @Column
    private String intro;

    @Column
    private String major;

    @Column
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column
    private Boolean isCurrentMember;

    @Column
    private String passWord;
    @Column
    private String imgPath = "default.png";
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private LocalDateTime createdAt;
}