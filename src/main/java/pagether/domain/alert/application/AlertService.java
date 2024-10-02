package pagether.domain.alert.application;

import org.springframework.scheduling.annotation.EnableAsync;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.alert.dto.res.AlertResponse;
import pagether.domain.alert.dto.res.RequestAlertResponse;
import pagether.domain.alert.exception.AlertNotFoundException;
import pagether.domain.alert.repository.AlertRepository;
import pagether.domain.alert.dto.res.SeparatedAlertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.follow.domain.Follow;
import pagether.domain.note.domain.Note;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public AlertResponse createAlert(User alarmSender, User alarmReceiver, AlertType alertType, Note note) {
        Alert alert = Alert.builder()
                .alarmSender(alarmSender)
                .alarmReceiver(alarmReceiver)
                .alertType(alertType)
                .note(note)
                .createdAt(LocalDateTime.now())
                .build();
        alert = alertRepository.save(alert);
        return new AlertResponse(alert);
    }

    public AlertResponse createAlert(User alarmSender, User alarmReceiver, AlertType alertType, Follow follow) {
        Alert alert = Alert.builder()
                .alarmSender(alarmSender)
                .alarmReceiver(alarmReceiver)
                .alertType(alertType)
                .follow(follow)
                .createdAt(LocalDateTime.now())
                .build();
        alert = alertRepository.save(alert);
        return new AlertResponse(alert);
    }

    public SeparatedAlertResponse getAllByUser(String userId) {
        User alarmReceiver = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Long lastSeenAlertId = alarmReceiver.getLastSeenAlertId();
        LocalDateTime weeksAgo = LocalDateTime.now().minusWeeks(2);

        List<Alert> alerts = alertRepository.findAllByAlarmReceiverAndAlertTypeNotAndCreatedAtAfterOrderByCreatedAtDesc(alarmReceiver, AlertType.FOLLOW_REQUEST, weeksAgo);
        List<Alert> requestAlerts = alertRepository.findAllByAlarmReceiverAndAlertTypeAndCreatedAtAfterOrderByCreatedAtDesc(alarmReceiver, AlertType.FOLLOW_REQUEST, weeksAgo);

        List<RequestAlertResponse> requestAlertResponses = requestAlerts.stream().map(RequestAlertResponse::new).toList();
        List<AlertResponse> alertResponses = alerts.stream().map(AlertResponse::new).toList();

        List<AlertResponse> readAlert = filterAlertsByReadStatus(alertResponses, lastSeenAlertId, true);
        List<AlertResponse> unreadAlert = filterAlertsByReadStatus(alertResponses, lastSeenAlertId, false);
        updateLastSeenAlertId(alarmReceiver, alerts);
        return new SeparatedAlertResponse(requestAlertResponses, readAlert, unreadAlert);
    }

    private List<AlertResponse> filterAlertsByReadStatus(List<AlertResponse> alertResponses, Long lastSeenAlertId, Boolean isGetReadAlert) {
        return alertResponses.stream()
                .filter(alertResponse -> isGetReadAlert ? alertResponse.getAlertId() <= lastSeenAlertId : alertResponse.getAlertId() > lastSeenAlertId)
                .collect(Collectors.toList());
    }

    private void updateLastSeenAlertId(User user, List<Alert> alertDatas) {
        Long latestNewsId = alertDatas.isEmpty() ? user.getLastSeenAlertId() : alertDatas.get(0).getAlertId();
        user.setLastSeenNewsId(latestNewsId);
        userRepository.save(user);
    }

    public void delete(Long alertId) {
        if (!alertRepository.existsById(alertId)) {
            throw new AlertNotFoundException();
        }
        alertRepository.deleteById(alertId);
    }
}