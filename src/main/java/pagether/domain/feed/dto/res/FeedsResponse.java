package pagether.domain.feed.dto.res;

import lombok.*;
import pagether.domain.feed.dto.FeedDTO;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class FeedsResponse {
    private List<FeedDTO> feeds;
    private int nextCursor;

    public FeedsResponse(List<FeedDTO> feeds, int nextCursor) {
        this.feeds = feeds;
        this.nextCursor = nextCursor;
    }
}
