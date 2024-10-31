package gachonherald.domain.section.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Section {
    @Id
    private Long sectionId;

    @Column(nullable = false)
    private String name;
}