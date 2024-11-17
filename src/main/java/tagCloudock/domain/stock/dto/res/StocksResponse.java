package tagCloudock.domain.stock.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tagCloudock.domain.stock.dto.StockDTO;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class StocksResponse {
    private List<StockDTO> stocks;
    @Builder
    public StocksResponse(List<StockDTO> stocks) {
        this.stocks = stocks;
    }
}
