package pagether.domain.chat.exception;

import pagether.global.config.exception.ApplicationException;
import pagether.domain.chat.presentation.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ChatRoomNotFoundException extends ApplicationException {
    public ChatRoomNotFoundException() {
        super(NOT_FOUND.value(), ResponseMessage.CHAT_ROOM_NOT_FOUND.getMessage());
    }
}
