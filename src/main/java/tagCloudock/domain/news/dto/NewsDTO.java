package tagCloudock.domain.news.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NewsDTO {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private String pubDate;
}
