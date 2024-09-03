package pagether.domain.chat.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddChatRoomRequest {
    private String roomId;
    private String borrowerId;
    private String renderId;
    private Integer postId;
}
