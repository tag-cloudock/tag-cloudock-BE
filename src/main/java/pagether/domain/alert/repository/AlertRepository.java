package pagether.domain.alert.repository;

import pagether.domain.alert.domain.Alert;
import pagether.domain.news.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByAlarmReceiverOrderByCreatedAtDesc(User alarmReceiver);
    List<Alert> findAllByAlarmReceiverAndCreatedAtAfterOrderByCreatedAtDesc(User alarmReceiver, LocalDateTime createdAt);
}
