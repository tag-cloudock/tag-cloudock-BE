package pagether.domain.chat.dto.res;

import pagether.domain.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomResponse {

    private Integer id;
    private String roomId;
    private String borrowerId;
    private String borrowerNickname;
    private String renderId;
    private String renderNickname;
    private Integer postId;
    private String postName;

    @Builder
    public ChatRoomResponse(ChatRoom chatRoom){
        id = chatRoom.getId();
        roomId = chatRoom.getRoomId();
        borrowerId = chatRoom.getBorrower().getId();
        borrowerNickname = chatRoom.getBorrower().getNickName();
        renderId = chatRoom.getLender().getId();
        renderNickname = chatRoom.getLender().getNickName();
        postId = chatRoom.getPost().getPostId();
        postName = chatRoom.getPost().getTitle();
    }
}
