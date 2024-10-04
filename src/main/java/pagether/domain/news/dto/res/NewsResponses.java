package pagether.domain.news.dto.res;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class NewsResponses {
    private List<NewsResponse> readNews;
    private List<NewsResponse> unreadNews;
    private Long nextCursor;

    @Builder
    public NewsResponses(List<NewsResponse> readNews, List<NewsResponse> unreadNews, Long nextCursor) {
        this.readNews = readNews;
        this.unreadNews = unreadNews;
        this.nextCursor = nextCursor;
    }
}
