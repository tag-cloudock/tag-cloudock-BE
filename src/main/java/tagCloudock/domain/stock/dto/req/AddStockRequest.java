package tagCloudock.domain.stock.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddStockRequest {
    private Long stockId;
    private String name;
}