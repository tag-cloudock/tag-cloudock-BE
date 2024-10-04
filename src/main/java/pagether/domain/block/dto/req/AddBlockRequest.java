package pagether.domain.block.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddBlockRequest {
    private String blockedUserId;
}