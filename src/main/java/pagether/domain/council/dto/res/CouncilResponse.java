package pagether.domain.council.dto.res;

import pagether.domain.council.domain.Council;
import pagether.domain.council_item.domain.CouncilItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouncilResponse {

    private Integer councilId;
    private String college;
    private String name;
    private String location;
    private String operatingHours;
    private String usageGuidelines;
    private Double latitude;
    private Double longitude;
    private String imgPath;
    private List<CouncilItem> items = new ArrayList<>();
    private Boolean isCouncilSelfManage;
    private Long heart;

    @Builder
    public CouncilResponse(Council council, String imgPath) {
        councilId = council.getCouncilId();
        college = council.getCollege();
        name = council.getName();
        location = council.getLocation();
        operatingHours = council.getOperatingHours();
        usageGuidelines = council.getUsageGuidelines();
        latitude = council.getLatitude();
        longitude = council.getLongitude();
        this.imgPath = imgPath;
        items = council.getItems();
        heart = council.getHeart();
        isCouncilSelfManage = council.getIsCouncilSelfManage();
    }

    @Builder
    public CouncilResponse(Council council) {
        councilId = council.getCouncilId();
        college = council.getCollege();
        name = council.getName();
        location = council.getLocation();
        operatingHours = council.getOperatingHours();
        usageGuidelines = council.getUsageGuidelines();
        items = council.getItems();
        heart = council.getHeart();
        isCouncilSelfManage = council.getIsCouncilSelfManage();
    }
}
