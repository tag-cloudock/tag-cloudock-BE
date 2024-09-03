package pagether.domain.message.application;

import pagether.domain.chat.domain.ChatRoom;
import pagether.domain.chat.exception.ChatRoomNotFoundException;
import pagether.domain.chat.repository.ChatRoomRepository;
import pagether.domain.message.domain.Message;
import pagether.domain.message.dto.res.MessageResponse;
import pagether.domain.message.exception.PermissionDeniedException;
import pagether.domain.message.repository.MessageRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.domain.UserType;
import pagether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public Message save(String message, String roomId, String userName, UserType userType) {
        User user = userRepository.findByUserId(userName).get();
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        LocalDateTime sentAt = LocalDateTime.now();

        Message saveMessage = Message.builder()
                .message(message)
                .sentAt(sentAt)
                .chatRoom(chatRoom)
                .user(user)
                .userType(userType)
                .build();
        return messageRepository.save(saveMessage);
    }

    public List<MessageResponse> getAllMessageByRoomId(String roomId, String userName) {

        if (!chatRoomRepository.existsByRoomId(roomId)) {
            throw new ChatRoomNotFoundException();
        }
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        if (!chatRoom.getBorrower().getUserId().equals(userName) && !chatRoom.getLender().getUserId().equals(userName)) {
            throw new PermissionDeniedException();
        }
        List<MessageResponse> messagesDTO = new ArrayList<>();
        List<Message> messages = messageRepository.findAllByChatRoomOrderBySentAtAsc(chatRoom);
        for (Message message : messages) {
            MessageResponse dto = new MessageResponse(message);
            messagesDTO.add(dto);
        }
        return messagesDTO;
    }
}
