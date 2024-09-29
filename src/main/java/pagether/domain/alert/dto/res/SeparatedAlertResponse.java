package pagether.domain.alert.dto.res;

import lombok.*;
import pagether.domain.news.dto.res.NewsResponse;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SeparatedAlertResponse {
    private List<RequestAlertResponse> requestAlerts;
    private List<AlertResponse> seenAlerts;
    private List<AlertResponse> unseenAlerts;

    @Builder
    public SeparatedAlertResponse(List<RequestAlertResponse> requestAlerts, List<AlertResponse> readNews, List<AlertResponse> unreadNews) {
        this.requestAlerts = requestAlerts;
        this.seenAlerts = readNews;
        this.unseenAlerts = unreadNews;
    }
}
