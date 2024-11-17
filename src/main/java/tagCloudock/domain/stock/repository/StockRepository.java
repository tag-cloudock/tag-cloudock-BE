package tagCloudock.domain.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tagCloudock.domain.stock.domain.Stock;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {
}