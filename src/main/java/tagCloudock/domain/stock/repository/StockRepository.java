package tagCloudock.domain.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tagCloudock.domain.stock.domain.Stock;
import tagCloudock.domain.user.domain.User;

import java.util.List;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByUser(User user);
}