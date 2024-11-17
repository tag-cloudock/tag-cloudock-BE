package tagCloudock.domain.user.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UpdateUserRequest {
    private final String nickname;
    private final String intro;
    private final String major;
    private final String email;
}
