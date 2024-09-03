package pagether.global.config.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import pagether.domain.chat.application.ChatService;
import pagether.domain.chat.domain.ChatRoom;
import pagether.domain.chat.dto.ChatDTO;
import pagether.domain.chat.exception.FailSendMessageException;
import pagether.domain.chat.repository.ChatRoomRepository;
import pagether.domain.message.application.MessageService;
import pagether.domain.message.domain.MessageType;
import pagether.domain.user.domain.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper mapper;
    private final ChatService chatService;
    private final MessageService messageService;
    private final ChatRoomRepository chatRoomRepository;

    private Map<WebSocketSession, Long> sessionToIdMap = new HashMap<>();
    private Map<Long, WebSocketSession> idToSessionMap = new HashMap<>();
    private Long session_Idx = 0L;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatDTO chatMessage = mapper.readValue(payload, ChatDTO.class);
        log.info("{} 연결됨", session.getId());
        handleAction(session, chatMessage, chatService);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long removedSessionIdx = sessionToIdMap.remove(session);
        idToSessionMap.remove(removedSessionIdx);
        log.info("{} 연결 끊김", session.getId());
        log.info("세션 메모리 크기 {}", sessionToIdMap.size());
    }

    public void handleAction(WebSocketSession session, ChatDTO message, ChatService chatService) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId());

        if (message.getType().equals(MessageType.ENTER)) {
            System.out.println("hello");
            session_Idx += 1;
            sessionToIdMap.put(session, session_Idx);
            idToSessionMap.put(session_Idx, session);

            if (message.getUserType().equals(UserType.BORROWER))
                chatRoom.setBorrowerSessionId(session_Idx);
            else if (message.getUserType().equals(UserType.LENDER))
                chatRoom.setLenderSessionId(session_Idx);
            chatRoomRepository.save(chatRoom);

//            message.setMessage(message.getSender() + " 님이 입장하셨습니다");
//            sendMessage(message, chatRoom, chatService);
        } else if (message.getType().equals(MessageType.TALK)) {
            messageService.save(message.getMessage(), message.getRoomId(), message.getSender(), message.getUserType());
            message.setMessage(message.getMessage());
            sendMessage(message, chatRoom, chatService);
        }
    }

    public <T> void sendMessage(T message, ChatRoom chatRoom, ChatService chatService) {
        try {
            chatService.sendMessage(idToSessionMap.get(chatRoom.getBorrowerSessionId()), message);
            chatService.sendMessage(idToSessionMap.get(chatRoom.getLenderSessionId()), message);
        } catch (Exception e) {
            throw new FailSendMessageException();
        }
    }
}