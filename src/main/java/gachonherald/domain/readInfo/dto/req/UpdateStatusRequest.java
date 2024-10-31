package gachonherald.domain.readInfo.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class UpdateStatusRequest {
    private Long readId;
}