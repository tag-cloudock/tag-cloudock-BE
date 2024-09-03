package pagether.domain.council_item.dto.req;

import pagether.domain.council_item.domain.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCouncilItemRequest {
    private Integer councilId;
    private String name;
    private ItemType type;
}
