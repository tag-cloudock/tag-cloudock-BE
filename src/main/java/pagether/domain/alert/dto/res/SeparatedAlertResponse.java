package pagether.domain.alert.dto.res;

import lombok.*;
import pagether.domain.news.dto.res.NewsResponse;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SeparatedAlertResponse {
    private List<AlertResponse> seenAlerts;
    private List<AlertResponse> unseenAlerts;

    @Builder
    public SeparatedAlertResponse(List<AlertResponse> readNews, List<AlertResponse> unreadNews) {
        this.seenAlerts = readNews;
        this.unseenAlerts = unreadNews;
    }
}
