package pagether.domain.chat.dto;

import pagether.domain.message.domain.MessageType;
import pagether.domain.user.domain.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatDTO {
    // 메시지  타입 : 입장, 채팅
    private UserType userType;
    private MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private LocalDateTime sentAt; // 채팅 발송 시간간
}