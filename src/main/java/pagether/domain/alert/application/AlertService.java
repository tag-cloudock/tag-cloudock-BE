package pagether.domain.alert.application;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.alert.dto.req.AddAlertRequest;
import pagether.domain.alert.dto.res.AlertResponse;
import pagether.domain.alert.exception.AlertNotFoundException;
import pagether.domain.alert.repository.AlertRepository;
import pagether.domain.alert.dto.res.SeparatedAlertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public AlertResponse save(User alarmSender, User alarmReceiver, AlertType alertType, Long note) {
        Alert alert = Alert.builder()
                .alarmSender(alarmSender)
                .alarmReceiver(alarmReceiver)
                .alertType(alertType)
                .note(note)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
        alert = alertRepository.save(alert);
        return new AlertResponse(alert);
    }
    public SeparatedAlertResponse getAllByUser(String userId) {
        User alarmReceiver = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Alert> alerts = alertRepository.findAllByAlarmReceiverOrderByCreatedAtDesc(alarmReceiver);

        List<AlertResponse> AlertResponses = alerts.stream()
                .map(AlertResponse::new)
                .toList();

        List<AlertResponse> readNews = AlertResponses.stream()
                .filter(AlertResponse::getIsRead)
                .collect(Collectors.toList());
        List<AlertResponse> unreadNews = AlertResponses.stream()
                .filter(AlertResponse -> !AlertResponse.getIsRead())
                .collect(Collectors.toList());

        markAllAsReadAsync(alerts);
        return new SeparatedAlertResponse(readNews, unreadNews);
    }

    @Async
    public CompletableFuture<Void> markAllAsReadAsync(List<Alert> alerts) {
        return CompletableFuture.runAsync(() -> {
            alerts.forEach(alert -> {
                alert.setIsRead(true);
                alertRepository.save(alert);
            });
        });
    }

    public void delete(Integer newsId) {
        if (!alertRepository.existsById(newsId)) {
            throw new AlertNotFoundException();
        }
        alertRepository.deleteById(newsId);
    }
}