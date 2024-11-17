package tagCloudock.domain.user.dto;

import tagCloudock.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReporterDTO {
    private Long reporterId;
    private String position;
    private String nickname;

    public ReporterDTO(User reporter) {
        reporterId = reporter.getId();
        position = reporter.getPosition().getDisplayName();
        nickname = reporter.getNickName();
    }
}
