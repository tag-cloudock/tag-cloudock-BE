package pagether.domain.alert.dto.res;

import lombok.*;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AlertResponses {
    private List<RequestAlertResponse> requestAlerts;
    private List<AlertResponse> seenAlerts;
    private List<AlertResponse> unseenAlerts;
    private Long nextCursor;

    public AlertResponses(List<AlertResponse> readNews, List<AlertResponse> unreadNews, Long nextCursor) {
        this.seenAlerts = readNews;
        this.unseenAlerts = unreadNews;
        this.nextCursor = nextCursor;
    }

    public AlertResponses(List<RequestAlertResponse> requestAlerts, Long nextCursor) {
        this.requestAlerts = requestAlerts;
        this.nextCursor = nextCursor;
    }
}
