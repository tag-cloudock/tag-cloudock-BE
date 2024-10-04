package pagether.domain.block.dto.res;

import lombok.*;
import pagether.domain.block.domain.Block;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BlockCountResponse {
    private Long blockCount;

    @Builder
    public BlockCountResponse(Long blockCount) {
        this.blockCount = blockCount;
    }
}
