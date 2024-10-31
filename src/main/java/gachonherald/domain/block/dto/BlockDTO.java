package gachonherald.domain.block.dto;

import lombok.*;
import gachonherald.domain.block.domain.Block;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlockDTO {
    private Long blockId;
    private String userId;
    private String userNickName;
    private String userAccountName;
    private String userProfileImgName;

    @Builder
    public BlockDTO(Block block) {
        blockId = block.getBlockId();
        userId = block.getBlocked().getUserId();
        userNickName = block.getBlocked().getNickName();
        userAccountName = block.getBlocked().getAccountName();
        userProfileImgName = block.getBlocked().getImgPath();
    }
}
