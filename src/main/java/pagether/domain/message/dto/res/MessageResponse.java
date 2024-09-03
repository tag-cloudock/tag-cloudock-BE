package pagether.domain.message.dto.res;

import pagether.domain.message.domain.Message;
import pagether.domain.user.domain.UserType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponse {

    private Long chatId;
    private String message;
    private LocalDateTime sentAt;
    private UserType userType;

    @Builder
    public MessageResponse(Message message) {
        chatId = message.getChatId();
        this.message = message.getMessage();
        sentAt = message.getSentAt();
        userType = message.getUserType();
    }
}
