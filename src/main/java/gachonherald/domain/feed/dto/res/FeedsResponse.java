package gachonherald.domain.feed.dto.res;

import lombok.*;
import gachonherald.domain.feed.dto.FeedDTO;

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
