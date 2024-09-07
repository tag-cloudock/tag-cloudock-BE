package pagether.domain.user.domain;

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

    @Column
    private String phone;

    @Column
    private String imgPath = "default.png";

    @Column
    private String passWord;

    @Column(nullable = false)
    private Long lastSeenNewsId;

    @Enumerated(EnumType.STRING)
    private Role role;
}