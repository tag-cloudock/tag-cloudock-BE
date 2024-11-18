package tagCloudock.domain.tag.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TagDTO {
    private String text;
    private int value;

    public TagDTO(String text, int value) {
        this.text = text;
        this.value = value;
    }
}
