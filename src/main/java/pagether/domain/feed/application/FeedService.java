package pagether.domain.feed.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.block.application.BlockService;
import pagether.domain.feed.domain.FetchFeedType;
import pagether.domain.feed.dto.FeedDTO;
import pagether.domain.feed.dto.res.FeedsResponse;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.domain.RequestStatus;
import pagether.domain.follow.repository.FollowRepository;
import pagether.domain.note.application.NoteService;
import pagether.domain.note.domain.Note;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import pagether.global.config.exception.IllegalArgumentException;
import pagether.global.config.exception.LastPageReachedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedService {
    private final FollowRepository followRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteService noteService;
    private final BlockService blockService;
    private final RedisTemplate<String, String> redisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final int WEEKS_TO_SUBTRACT = 5;
    private static final int PAGE_SIZE = 10;

    public FeedsResponse getFeeds(FetchFeedType type, int cursor, String userId) throws JsonProcessingException {
        if (type.equals(FetchFeedType.FOLLOW))
            return getFollowFeeds(cursor, userId);
        else if (type.equals(FetchFeedType.POPULAR))
            return getPopularFeeds(cursor, userId);
        else if (type.equals(FetchFeedType.RECOMMENDED))
            return getRecommendedFeeds(cursor, userId);
        throw new IllegalArgumentException();
    }

    public FeedsResponse getFollowFeeds(int cursor, String userId) throws JsonProcessingException {
        if (cursor == 0){
            makeFollowFeeds(userId);
        }
        String feedsJson = redisTemplate.opsForValue().get(userId + ":followFeeds");
        if (feedsJson == null)
            throw new LastPageReachedException();
        List<FeedDTO> feeds =  objectMapper.readValue(feedsJson, new TypeReference<List<FeedDTO>>() {});
        int end = Math.min(cursor+PAGE_SIZE, feeds.size());
        if (cursor > feeds.size() || cursor < 0) {
            throw new LastPageReachedException();
        }
        return new FeedsResponse(feeds.subList(cursor, end), cursor+PAGE_SIZE);
    }

    public void makeFollowFeeds(String userId) throws JsonProcessingException {
        List<FeedDTO> feeds = new ArrayList<>();
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);

        List<Follow> followedUsers = followRepository.findAllByFollowerAndRequestStatus(user, RequestStatus.ACCEPTED);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterDate = now.minusWeeks(WEEKS_TO_SUBTRACT);

        for (Follow follow : followedUsers) {
            User followee = follow.getFollowee();
            List<Note> notes = noteRepository.findAllByUserAndCreatedAtAfterOrderByNoteIdDesc(followee, afterDate);
            for (Note note : notes)
                feeds.add(new FeedDTO(note, note.getHeartCount(), noteService.isHeartClicked(note, user)));
        }
        feeds.sort(Comparator.comparing(FeedDTO::getId).reversed());

        String feedsJson = objectMapper.writeValueAsString(feeds);
        redisTemplate.opsForValue().set(userId + ":followFeeds", feedsJson);
    }

    public FeedsResponse getPopularFeeds(int cursor, String userId) throws JsonProcessingException {
        if (cursor == 0){
            makePopularFeeds(userId);
        }
        String feedsJson = redisTemplate.opsForValue().get(userId + ":popularFeeds");
        if (feedsJson == null)
            throw new LastPageReachedException();
        List<FeedDTO> feeds =  objectMapper.readValue(feedsJson, new TypeReference<List<FeedDTO>>() {});
        int end = Math.min(cursor+PAGE_SIZE, feeds.size());
        if (cursor > feeds.size() || cursor < 0) {
            throw new LastPageReachedException();
        }
        return new FeedsResponse(feeds.subList(cursor, end), cursor+PAGE_SIZE);
    }

    public void makePopularFeeds(String userId) throws JsonProcessingException {
        List<FeedDTO> feeds = new ArrayList<>();
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterDate = now.minusWeeks(WEEKS_TO_SUBTRACT);
        List<Note> notes = noteRepository.findAllByCreatedAtAfterOrderByHeartCountDesc(afterDate);
        for (Note note : notes)
            if (!(blockService.isBlocked(user, note.getUser()) || blockService.isBlocked(note.getUser(), user)))
                feeds.add(new FeedDTO(note, note.getHeartCount(), noteService.isHeartClicked(note, user)));
        feeds.sort(Comparator.comparing(FeedDTO::getId).reversed());
        String feedsJson = objectMapper.writeValueAsString(feeds);
        redisTemplate.opsForValue().set(userId + ":popularFeeds", feedsJson);
    }

    public FeedsResponse getRecommendedFeeds(int cursor, String userId) {
        return new FeedsResponse();
    }
}
