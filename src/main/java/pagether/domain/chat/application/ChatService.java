package pagether.domain.chat.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import pagether.domain.chat.domain.ChatRoom;
import pagether.domain.chat.dto.req.AddChatRoomRequest;
import pagether.domain.chat.dto.res.ChatRoomResponse;
import pagether.domain.chat.dto.res.ChatRoomsResponse;
import pagether.domain.chat.exception.FailSendMessageException;
import pagether.domain.chat.repository.ChatRoomRepository;
import pagether.domain.message.domain.Message;
import pagether.domain.message.repository.MessageRepository;
import pagether.domain.post.domain.Post;
import pagether.domain.post.repository.PostRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.domain.UserType;
import pagether.domain.user.repository.UserRepository;
import pagether.global.config.alertTalk.AlertTalkSender;
import pagether.global.config.alertTalk.TemplateType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Data
@Service
public class ChatService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final ObjectMapper mapper;
    private final AlertTalkSender alertTalkSender;
    private static final String NO_MESSAGE = "no message";

    public ChatRoomResponse save(AddChatRoomRequest request) {
        String roomId = UUID.randomUUID().toString();
        User borrower = userRepository.findById(request.getBorrowerId()).get();
        User lender = userRepository.findById(request.getRenderId()).get();
        Post post = postRepository.findById(request.getPostId()).get();

        Optional<ChatRoom> alreadyExistChatRoom = chatRoomRepository.findByBorrowerAndLenderAndPost(borrower, lender, post);
        if (alreadyExistChatRoom.isPresent()) {
            return new ChatRoomResponse(alreadyExistChatRoom.get());
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .borrower(borrower)
                .lender(lender)
                .post(post)
                .createdAt(LocalDateTime.now())
                .build();

        alertTalkSender.sendKakaoAlertTalk(lender.getNickName(), post.getTitle(), borrower.getPhone(), TemplateType.START_CHAT);
        return new ChatRoomResponse(chatRoomRepository.save(chatRoom));
    }

    public List<List<ChatRoomsResponse>> getAllByUser(String userName) {
        if (userRepository.existsUserByUserId(userName)) {
            User user = userRepository.findByUserId(userName).get();

            List<List<ChatRoom>> chatRoomsBoth = new ArrayList<>();

            List<ChatRoom> chatRoomsForBorrowing = chatRoomRepository.findAllByBorrowerOrderByCreatedAtDesc(user);
            chatRoomsBoth.add(chatRoomsForBorrowing);
            List<ChatRoom> chatRoomsForLending = chatRoomRepository.findAllByLenderOrderByCreatedAtDesc(user);
            chatRoomsBoth.add(chatRoomsForLending);

            List<List<ChatRoomsResponse>> BorrowAndLendDTO = new ArrayList<>();
            for (int i = 0; i < chatRoomsBoth.size(); i++) {
                List<ChatRoom> chatRooms = chatRoomsBoth.get(i);
                List<ChatRoomsResponse> chatRoomsDTO = new ArrayList<>();
                for (ChatRoom chatRoom : chatRooms) {
                    Optional<Message> optionalLastMessage = messageRepository.findFirstByChatRoomOrderBySentAtDesc(chatRoom);
                    String lastMessage = NO_MESSAGE;
                    LocalDateTime lastMessageTime = LocalDateTime.now();
                    if (optionalLastMessage.isPresent()) {
                        lastMessage = optionalLastMessage.get().getMessage();
                        lastMessageTime = optionalLastMessage.get().getSentAt();
                    }
                    UserType userType = i == 0 ? UserType.BORROWER : UserType.LENDER;
                    chatRoomsDTO.add(new ChatRoomsResponse(chatRoom, userType, lastMessage, lastMessageTime));
                }
                BorrowAndLendDTO.add(chatRoomsDTO);
            }
            return BorrowAndLendDTO;
        }
        return new ArrayList<>();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            if (session != null) {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
            }
        } catch (IOException e) {
            throw new FailSendMessageException();
        }
    }

}
