package pagether.domain.count.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Count {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitCountId;

    @Column(nullable = false)
    private Long count;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate day;
}