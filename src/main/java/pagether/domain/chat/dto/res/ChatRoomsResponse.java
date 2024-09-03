package pagether.domain.chat.dto.res;

import pagether.domain.chat.domain.ChatRoom;
import pagether.domain.user.domain.UserType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomsResponse {
    private Integer id;
    private String roomId;
    private Integer postId;
    private String postImgPath;
    private String lenderImgPath;
    private String borrowerImgPath;
    private UserType userType;
    private String borrowerNickname;
    private String borrowerId;
    private String lenderNickname;
    private String lenderId;
    private String lastMessage;
    private boolean isDone;
    private LocalDateTime lastMessageTime;

    @Builder
    public ChatRoomsResponse(ChatRoom chatRoom, UserType userType, String lastMessage, LocalDateTime lastMessageTime){
        id = chatRoom.getId();
        roomId = chatRoom.getRoomId();
        postId = chatRoom.getPost().getPostId();
        this.userType = userType;
        borrowerNickname = chatRoom.getBorrower().getNickName();
        lenderNickname = chatRoom.getLender().getNickName();
        lenderId = chatRoom.getLender().getId();
        borrowerId = chatRoom.getBorrower().getId();
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        postImgPath = chatRoom.getPost().getImgPath();
        lenderImgPath = chatRoom.getLender().getImgPath();
        borrowerImgPath = chatRoom.getBorrower().getImgPath();
        this.isDone = chatRoom.getPost().getIsClose();
    }
}
