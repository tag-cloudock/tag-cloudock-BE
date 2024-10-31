package gachonherald.domain.category.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Category {
    @Id
    private Long categoryId;

    @Column(nullable = false)
    private String name;
}