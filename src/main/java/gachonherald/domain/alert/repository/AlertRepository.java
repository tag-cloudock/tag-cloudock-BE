package gachonherald.domain.alert.repository;

import org.springframework.data.domain.Pageable;
import gachonherald.domain.alert.domain.Alert;
import gachonherald.domain.alert.domain.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.user.domain.User;

import java.util.List;

@Repository
@Transactional
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByAlarmReceiverAndAlertTypeAndAlertIdLessThanOrderByAlertIdDesc(User alarmReceiver, AlertType alertType, Long cursor, Pageable pageable);
    List<Alert> findAllByAlarmReceiverAndAlertTypeNotAndAlertIdLessThanOrderByAlertIdDesc(User alarmReceiver, AlertType alertType, Long cursor, Pageable pageable);
}
