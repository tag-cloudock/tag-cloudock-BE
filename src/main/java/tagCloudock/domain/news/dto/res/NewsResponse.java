package tagCloudock.domain.news.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tagCloudock.domain.news.dto.NewsDTO;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class NewsResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NewsDTO> items;
}
