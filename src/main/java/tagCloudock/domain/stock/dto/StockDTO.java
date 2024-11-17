package tagCloudock.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tagCloudock.domain.stock.domain.Stock;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StockDTO {
    private Long stockId;
    private String imageName;
    private String name;


    public StockDTO(Stock stock) {
        stockId = stock.getStockId();
        imageName = stock.getImageName();
        name = stock.getName();
    }
}
