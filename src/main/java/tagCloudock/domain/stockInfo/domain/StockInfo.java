package tagCloudock.domain.stockInfo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "stockInfo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class StockInfo {
    @Id
    private String stockCode;

    @Column(nullable = false)
    private String name;
}