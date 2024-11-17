package tagCloudock.domain.stock.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Stock {
    @Id
    private Long stockId;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String name;
}