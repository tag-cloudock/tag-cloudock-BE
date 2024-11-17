package tagCloudock.domain.user.dto.res;

import tagCloudock.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporterResponse {
    private String nickname;
    private String imgPath;
    private String intro;
    private String email;
    private String major;
    private String position;
    public ReporterResponse(User user) {
        this.nickname = user.getNickName();
        this.imgPath = user.getImgPath();
        this.intro = user.getIntro();
        this.email = user.getEmail();
        this.major = user.getMajor();
        this.position = user.getPosition().getDisplayName();
    }
}