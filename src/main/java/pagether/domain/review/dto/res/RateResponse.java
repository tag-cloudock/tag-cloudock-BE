package pagether.domain.review.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateResponse {
    private Integer loveCount;
    private Integer goodCount;
    private Integer badCount;
}
