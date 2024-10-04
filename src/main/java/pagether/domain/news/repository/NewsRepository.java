package pagether.domain.news.repository;

import org.springframework.data.domain.Pageable;
import pagether.domain.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByNewsIdLessThanOrderByNewsIdDesc(Long newsId, Pageable pageable);
}