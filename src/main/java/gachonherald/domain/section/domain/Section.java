package gachonherald.domain.section.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "sections")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Section {
    @Id
    private Long sectionId;

    @Column
    @Enumerated(EnumType.STRING)
    private SectionStatus status;

    @Column
    private Long orderNumber;

    @Column(nullable = false)
    private String name;
}