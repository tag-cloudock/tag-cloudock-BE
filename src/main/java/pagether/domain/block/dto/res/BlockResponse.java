package pagether.domain.block.dto.res;

import lombok.*;
import pagether.domain.block.domain.Block;
import pagether.domain.follow.domain.Follow;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlockResponse {
    private Long blockId;
    private User blocked;
    private User blocking;
    private LocalDateTime createdAt;

    @Builder
    public BlockResponse(Block block) {
        blockId = block.getBlockId();
        blocked = block.getBlocked();
        blocking = block.getBlocking();
        createdAt = block.getCreatedAt();
    }
}
