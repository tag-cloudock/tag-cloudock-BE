package tagCloudock.domain.stockInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tagCloudock.domain.stock.domain.Stock;
import tagCloudock.domain.stockInfo.domain.StockInfo;

import java.util.List;

@Repository
@Transactional
public interface StockInfoRepository extends JpaRepository<StockInfo, String> {

    List<StockInfo> findByStockCodeContaining(String keyword);
    List<StockInfo> findByNameContaining(String keyword);
}