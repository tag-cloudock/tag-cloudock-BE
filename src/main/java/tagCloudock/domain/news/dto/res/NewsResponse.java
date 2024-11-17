package tagCloudock.domain.news.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tagCloudock.domain.news.dto.NewsDTO;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class NewsResponse {
    private List<NewsDTO> news;
    private int pageCount;

    @Builder
    public NewsResponse(List<NewsDTO> news, int pageCount) {
        this.news = news;
        this.pageCount = pageCount;
    }
}
