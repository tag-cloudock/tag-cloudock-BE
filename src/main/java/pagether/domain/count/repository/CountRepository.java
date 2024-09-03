package pagether.domain.count.repository;

import pagether.domain.count.domain.Count;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
@Transactional
public interface CountRepository extends JpaRepository<Count, Integer> {
    boolean existsByDayAndName(LocalDate day, String name);

    Count findByDayAndName(LocalDate day, String name);
}
