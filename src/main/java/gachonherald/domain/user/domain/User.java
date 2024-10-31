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
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private String passWord;

    @Column
    private String imgPath = "default.png";

    @Enumerated(EnumType.STRING)
    private Role role;
}