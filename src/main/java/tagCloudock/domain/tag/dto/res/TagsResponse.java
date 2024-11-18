package tagCloudock.domain.tag.dto.res;

import tagCloudock.domain.tag.dto.TagDTO;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class TagsResponse {
    private List<TagDTO> tags;
    @Builder
    public TagsResponse(List<TagDTO> tags) {
        this.tags = tags;
    }
}
