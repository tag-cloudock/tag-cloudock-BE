package tagCloudock.domain.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class TagDTO {
    private Long tagId;
    private String name;
    private Long amount;


    public TagDTO(Long tagId, String name, Long amount) {
        this.tagId = tagId;
        this.name = name;
        this.amount = amount;
    }
}
