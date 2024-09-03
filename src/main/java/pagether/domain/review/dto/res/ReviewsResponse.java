package pagether.domain.review.dto.res;

import pagether.domain.review.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ReviewsResponse {
    private Integer loveCount;
    private Integer goodCount;
    private Integer badCount;

    private List<Review> reviews;

    public ReviewsResponse(Integer loveCount, Integer goodCount, Integer badCount, List<Review> reviews) {
        this.loveCount = loveCount;
        this.goodCount = goodCount;
        this.badCount = badCount;
        this.reviews = reviews;
    }
}
