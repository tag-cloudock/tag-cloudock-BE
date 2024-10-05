package pagether.domain.alert.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.alert.domain.FetchAlarmType;
import pagether.domain.alert.dto.res.AlertResponse;
import pagether.domain.alert.dto.res.RequestAlertResponse;
import pagether.domain.alert.exception.AlertNotFoundException;
import pagether.domain.alert.repository.AlertRepository;
import pagether.domain.alert.dto.res.AlertResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.checker.application.CheckerService;
import pagether.domain.follow.domain.Follow;
import pagether.domain.note.domain.Note;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import pagether.global.config.exception.IllegalArgumentException;
import pagether.global.config.exception.LastPageReachedException;

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
    private final CheckerService checkerService;
    public static final int PAGE_SIZE = 10;
    private static final Pageable PAGEABLE = PageRequest.of(0, PAGE_SIZE);


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

    public AlertResponses getAlertsByUser(FetchAlarmType type, String userId, Long cursor) {
        if (type.equals(FetchAlarmType.GENERAL))
            return getGeneralAlertsByUser(userId, cursor);
        else if (type.equals(FetchAlarmType.REQUEST))
            return getRequestAlertsByUser(userId, cursor);
        throw new IllegalArgumentException();
    }

    public AlertResponses getRequestAlertsByUser(String userId, Long cursor) {
        User alarmReceiver = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Alert> requestAlerts = alertRepository.findAllByAlarmReceiverAndAlertTypeAndAlertIdLessThanOrderByAlertIdDesc(alarmReceiver, AlertType.FOLLOW_REQUEST, cursor, PAGEABLE);
        checkerService.checkLastPage(requestAlerts);
        List<RequestAlertResponse> requestAlertResponses = requestAlerts.stream().map(RequestAlertResponse::new).toList();
        return new AlertResponses(requestAlertResponses, requestAlerts.get(requestAlerts.size()-1).getAlertId());
    }

    public AlertResponses getGeneralAlertsByUser(String userId, Long cursor) {
        User alarmReceiver = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Long lastSeenAlertId = alarmReceiver.getLastSeenAlertId();
        List<Alert> alerts = alertRepository.findAllByAlarmReceiverAndAlertTypeNotAndAlertIdLessThanOrderByAlertIdDesc(alarmReceiver, AlertType.FOLLOW_REQUEST, cursor, PAGEABLE);
        checkerService.checkLastPage(alerts);
        List<AlertResponse> alertResponses = alerts.stream().map(AlertResponse::new).toList();
        List<AlertResponse> readAlert = filterAlertsByReadStatus(alertResponses, lastSeenAlertId, true);
        List<AlertResponse> unreadAlert = filterAlertsByReadStatus(alertResponses, lastSeenAlertId, false);
        updateLastSeenAlertId(alarmReceiver, alerts);
        return new AlertResponses(readAlert, unreadAlert, alerts.get(alerts.size()-1).getAlertId());
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
        if (!alertRepository.existsById(alertId))
            throw new AlertNotFoundException();
        alertRepository.deleteById(alertId);
    }
}