package gachonherald.domain.follow.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddFollowRequest {
    private String followeeId;
}