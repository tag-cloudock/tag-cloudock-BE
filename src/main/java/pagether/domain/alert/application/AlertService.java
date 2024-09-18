package pagether.domain.alert.application;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.alert.dto.res.AlertResponse;
import pagether.domain.alert.exception.AlertNotFoundException;
import pagether.domain.alert.repository.AlertRepository;
import pagether.domain.alert.dto.res.SeparatedAlertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.news.domain.News;
import pagether.domain.note.domain.Note;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.note.repository.NoteRepository;
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
    private final NoteRepository noteRepository;

    public AlertResponse createAlert(User alarmSender, User alarmReceiver, AlertType alertType, Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
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
    public SeparatedAlertResponse getAllByUser(String userId) {
        User alarmReceiver = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        LocalDateTime weeksAgo = LocalDateTime.now().minusWeeks(2);
        List<Alert> alerts = alertRepository.findAllByAlarmReceiverAndCreatedAtAfterOrderByCreatedAtDesc(alarmReceiver, weeksAgo);

        List<AlertResponse> alertResponses = alerts.stream()
                .map(AlertResponse::new)
                .toList();

        Long lastSeenAlertId = alarmReceiver.getLastSeenAlertId();

        List<AlertResponse> readNews = alertResponses.stream()
                .filter(alertResponse -> alertResponse.getAlertId() <= lastSeenAlertId)
                .collect(Collectors.toList());

        List<AlertResponse> unreadNews = alertResponses.stream()
                .filter(alertResponse -> alertResponse.getAlertId() > lastSeenAlertId)
                .collect(Collectors.toList());
        updateLastSeenAlertId(alarmReceiver, alerts);
        return new SeparatedAlertResponse(readNews, unreadNews);
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