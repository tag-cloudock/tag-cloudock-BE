package pagether.domain.readInfo.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Jacksonized
@Builder
public class UpdateDateRequest {
    private Long readId;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
}