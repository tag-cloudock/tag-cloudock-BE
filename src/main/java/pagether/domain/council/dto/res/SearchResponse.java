package pagether.domain.council.dto.res;

import pagether.domain.council.domain.Council;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchResponse {
    private Integer councilId;
    private String councilName;
    private String imgPath;
    
    @Builder
    public SearchResponse(Council council) {
        councilId = council.getCouncilId();
        councilName = council.getName();
        imgPath = council.getManager().getImgPath();
    }
}
