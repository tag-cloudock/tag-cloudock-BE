package pagether.domain.follow.dto.res;

import lombok.*;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.dto.FollowDTO;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;
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
