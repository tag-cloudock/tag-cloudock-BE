package pagether.domain.news.dto.res;

import lombok.*;
import pagether.domain.news.domain.News;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SeparatedNewsResponse {
    private List<NewsResponse> readNews;
    private List<NewsResponse> unreadNews;

    @Builder
    public SeparatedNewsResponse(List<NewsResponse> readNews,List<NewsResponse> unreadNews) {
        this.readNews = readNews;
        this.unreadNews = unreadNews;
    }
}
