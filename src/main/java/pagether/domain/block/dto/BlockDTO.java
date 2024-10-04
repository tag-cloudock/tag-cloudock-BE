package pagether.domain.block.dto;

import lombok.*;
import pagether.domain.block.domain.Block;
import pagether.domain.follow.domain.Follow;
import pagether.domain.user.domain.User;


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
