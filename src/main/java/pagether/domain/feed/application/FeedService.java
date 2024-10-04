package pagether.domain.feed.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.block.application.BlockService;
import pagether.domain.book.domain.Book;
import pagether.domain.book.dto.req.AddBookRequest;
import pagether.domain.book.dto.res.BookResponse;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.feed.dto.res.FeedDTO;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.domain.RequestStatus;
import pagether.domain.follow.repository.FollowRepository;
import pagether.domain.heart.application.HeartService;
import pagether.domain.heart.repository.HeartRepository;
import pagether.domain.note.application.NoteService;
import pagether.domain.note.domain.Note;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private static final int WEEKS_TO_SUBTRACT = 4;

    public List<FeedDTO> getFollowFeeds(String userId) {
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
        return feeds;
    }

    public List<FeedDTO> getPopularFeeds(String userId) {
        List<FeedDTO> feeds = new ArrayList<>();
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterDate = now.minusWeeks(WEEKS_TO_SUBTRACT);
        List<Note> notes = noteRepository.findAllByCreatedAtAfterOrderByHeartCountDesc(afterDate);
        for (Note note : notes)
            if (!(blockService.isBlocked(user, note.getUser()) || blockService.isBlocked(note.getUser(), user)))
                feeds.add(new FeedDTO(note, note.getHeartCount(), noteService.isHeartClicked(note, user)));
        feeds.sort(Comparator.comparing(FeedDTO::getId).reversed());
        return feeds;
    }

    public List<FeedDTO> getRecommendedFeeds(String userId) {
        List<FeedDTO> feeds = new ArrayList<>();
        return feeds;
    }
}
