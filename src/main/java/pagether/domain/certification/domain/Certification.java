package pagether.domain.certification.domain;
import pagether.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity()
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certiId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
//    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String studentIdNumber;

    @Column(nullable = false)
    private LocalDateTime requestAt;

    @Column
    private String imgPath = "default.png";
}