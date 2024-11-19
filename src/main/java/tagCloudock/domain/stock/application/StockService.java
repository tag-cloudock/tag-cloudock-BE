package tagCloudock.domain.stock.application;

import tagCloudock.domain.stock.domain.Stock;
import tagCloudock.domain.stock.dto.StockDTO;
import tagCloudock.domain.stock.dto.req.AddStockRequest;
import tagCloudock.domain.stock.dto.res.StocksResponse;
import tagCloudock.domain.stock.exception.StockAlreadyExistException;
import tagCloudock.domain.stock.exception.StockNotFoundException;
import tagCloudock.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tagCloudock.domain.stockInfo.domain.StockInfo;
import tagCloudock.domain.stockInfo.exception.StockInfoNotFoundException;
import tagCloudock.domain.stockInfo.repository.StockInfoRepository;
import tagCloudock.domain.user.domain.User;
import tagCloudock.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockInfoRepository stockInfoRepository;
    private final UserRepository userRepository;
    public void save(AddStockRequest request, String userId) {
        StockInfo stockInfo = stockInfoRepository.findById(request.getStockCode()).orElseThrow(StockInfoNotFoundException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(StockInfoNotFoundException::new);
        if (stockRepository.existsByUserAndStockInfo(user, stockInfo)){
            throw new StockAlreadyExistException();
        }
        Stock stock = Stock.builder()
                .stockInfo(stockInfo)
                .user(user)
                .build();
        stockRepository.save(stock);
    }

    public StocksResponse getList(String userId) {
        List<StockDTO> response = new ArrayList<>();
        User user = userRepository.findByUserId(userId).orElseThrow(StockInfoNotFoundException::new);
        List<Stock> stocks = stockRepository.findAllByUser(user);
        for(Stock stock : stocks) response.add(new StockDTO(stock));
        return new StocksResponse(response);
    }

    public StocksResponse search(String keyword) {
        List<StockDTO> response = new ArrayList<>();
        List<StockInfo> resultByCode = stockInfoRepository.findByStockCodeContaining(keyword);
        List<StockInfo> resultByName = stockInfoRepository.findByNameContaining(keyword);

        // 결과를 response 리스트에 추가
        for (StockInfo stock : resultByCode) {
            response.add(new StockDTO(stock));
        }
        for (StockInfo stock : resultByName) {
            response.add(new StockDTO(stock));
        }

        // 앞 5개의 결과만 잘라서 반환
        int size = response.size();
        List<StockDTO> firstFiveResults = response.subList(0, Math.min(5, size)); // 리스트의 크기가 5보다 작을 수 있음

        return new StocksResponse(firstFiveResults);
    }

    public void delete(String stockCode, String userId) {
        StockInfo stockInfo = stockInfoRepository.findById(stockCode).orElseThrow(StockInfoNotFoundException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(StockInfoNotFoundException::new);
        Stock stock = stockRepository.findByUserAndStockInfo(user, stockInfo);
        stockRepository.delete(stock);
    }
}