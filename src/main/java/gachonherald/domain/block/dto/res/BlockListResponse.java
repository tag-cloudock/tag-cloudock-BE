package gachonherald.domain.block.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import gachonherald.domain.block.dto.BlockDTO;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BlockListResponse {
    private List<BlockDTO> userList;
    private Long nextCursor;

    @Builder
    public BlockListResponse(List<BlockDTO> userList, Long nextCursor) {
        this.userList = userList;
        this.nextCursor = nextCursor;
    }
}
