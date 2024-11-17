package tagCloudock.domain.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class NewsDTO {
    private Long newsId;
    private String url;
    private String title;


    public NewsDTO(Long newsId, String url, String title) {
        this.newsId = newsId;
        this.url = url;
        this.title = title;
    }
}
