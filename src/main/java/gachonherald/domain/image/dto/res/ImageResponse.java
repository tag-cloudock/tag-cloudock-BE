package gachonherald.domain.image.dto.res;

import gachonherald.domain.user.domain.User;
import lombok.AllArgsConstructor;
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