package pagether.domain.council.dto.res;

import pagether.domain.council.domain.Council;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouncilsResponse {

    private Integer councilId;
    private String college;
    private String name;
    private String imgPath;
    private Integer providedItemCount;
    private Integer rentalItemCount;
    private Boolean isCouncilSelfManage;
    private Long heart;

    @Builder
    public CouncilsResponse(Council council, Integer pic, Integer ric, String imgPath) {
        councilId = council.getCouncilId();
        college = council.getCollege();
        name = council.getName();
        providedItemCount = pic;
        rentalItemCount = ric;
        this.imgPath = imgPath;
        heart = council.getHeart();
        isCouncilSelfManage = council.getIsCouncilSelfManage();
    }
}
