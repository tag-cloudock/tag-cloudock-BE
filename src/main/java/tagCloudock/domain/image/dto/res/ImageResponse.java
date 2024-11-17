package tagCloudock.domain.image.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ImageResponse {
    private String imageName;
    public ImageResponse(String imageName) {
        this.imageName = imageName;
    }
}