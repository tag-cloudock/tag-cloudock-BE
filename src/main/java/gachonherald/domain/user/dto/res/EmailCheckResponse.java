package gachonherald.domain.user.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class EmailCheckResponse {
    private int status;
    public EmailCheckResponse(int status) {
        this.status = status;
    }
}
