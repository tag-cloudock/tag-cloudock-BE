package tagCloudock.domain.oauth.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Jacksonized
@Builder
public class KakaoSignUpRequest {
    private String code;
}
