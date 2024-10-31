package gachonherald.domain.block.dto.res;

import lombok.*;

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
