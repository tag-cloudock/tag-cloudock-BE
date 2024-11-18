package tagCloudock.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tagCloudock.domain.stock.domain.Stock;
import tagCloudock.domain.stockInfo.domain.StockInfo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StockDTO {
    private String stockCode;
    private String name;


    public StockDTO(Stock stock) {
        stockCode = stock.getStockInfo().getStockCode();
        name = stock.getStockInfo().getName();
    }

    public StockDTO(StockInfo stockInfo) {
        stockCode = stockInfo.getStockCode();
        name = stockInfo.getName();
    }
}
