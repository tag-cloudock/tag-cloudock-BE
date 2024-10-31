package gachonherald.domain.news.dto.res;

import gachonherald.domain.news.domain.News;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsResponse {
    private Long newsId;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public NewsResponse(News news) {
        newsId = news.getNewsId();
        content = news.getContent();
        createdAt = news.getCreatedAt();
    }
}
