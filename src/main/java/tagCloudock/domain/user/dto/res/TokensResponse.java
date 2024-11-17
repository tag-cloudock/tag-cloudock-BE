package tagCloudock.domain.user.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
public class TokensResponse {
    private String accessToken;
    private String refreshToken;


    public TokensResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}