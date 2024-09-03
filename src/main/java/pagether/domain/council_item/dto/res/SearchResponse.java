package pagether.domain.council_item.dto.res;

import pagether.domain.council_item.domain.CouncilItem;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchResponse {
    private Integer councilId;
    private String councilName;
    private String name;


    @Builder
    public SearchResponse(CouncilItem councilItem) {
        councilId = councilItem.getCouncil().getCouncilId();
        councilName = councilItem.getCouncil().getName();
        name = councilItem.getName();
    }
}
