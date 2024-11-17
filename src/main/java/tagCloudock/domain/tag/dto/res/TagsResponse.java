package tagCloudock.domain.tag.dto.res;

import tagCloudock.domain.tag.dto.TagDTO;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class TagsResponse {
    private List<TagDTO> articles;
    private int pageCount;
    @Builder
    public TagsResponse(List<TagDTO> articles, int pageCount) {
        this.articles = articles;
        this.pageCount = pageCount;
    }
    @Builder
    public TagsResponse(List<TagDTO> articles) {
        this.articles = articles;
    }
}
