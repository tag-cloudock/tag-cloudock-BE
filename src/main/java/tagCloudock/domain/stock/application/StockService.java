package tagCloudock.domain.stock.application;

import tagCloudock.domain.stock.domain.Stock;
import tagCloudock.domain.stock.dto.StockDTO;
import tagCloudock.domain.stock.dto.req.AddStockRequest;
import tagCloudock.domain.stock.dto.res.StocksResponse;
import tagCloudock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    public void save(AddStockRequest request) {
        Stock stock = Stock.builder()
                .stockId(request.getStockId())
                .imageName("")
                .name(request.getName())
                .build();
        stockRepository.save(stock);
    }

    public StocksResponse getList() {
        List<StockDTO> response = new ArrayList<>();
        List<Stock> stocks = stockRepository.findAll();
        for(Stock stock : stocks) response.add(new StockDTO(stock));
        return new StocksResponse(response);
    }
}