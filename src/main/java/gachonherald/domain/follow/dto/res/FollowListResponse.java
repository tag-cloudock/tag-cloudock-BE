package gachonherald.domain.follow.dto.res;

import lombok.*;
import gachonherald.domain.follow.dto.FollowDTO;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class FollowListResponse {
    private List<FollowDTO> userList;
    private Long nextCursor;

    @Builder
    public FollowListResponse(List<FollowDTO> userList, Long nextCursor) {
        this.userList = userList;
        this.nextCursor = nextCursor;
    }
}
