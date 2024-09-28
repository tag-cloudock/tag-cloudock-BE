package pagether.domain.follow.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.alert.dto.res.AlertResponse;
import pagether.domain.alert.dto.res.SeparatedAlertResponse;
import pagether.domain.alert.exception.AlertNotFoundException;
import pagether.domain.alert.repository.AlertRepository;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.dto.req.AddFollowRequest;
import pagether.domain.follow.dto.res.FollowCountResponse;
import pagether.domain.follow.dto.res.FollowListResponse;
import pagether.domain.follow.dto.res.FollowResponse;
import pagether.domain.follow.exception.AlreadyFollowedAndNotFollowedException;
import pagether.domain.follow.exception.FollowNotAllowedException;
import pagether.domain.follow.exception.FollowNotFoundException;
import pagether.domain.follow.repository.FollowRepository;
import pagether.domain.heart.domain.Heart;
import pagether.domain.heart.exception.AlreadyClickedAndNotClickedException;
import pagether.domain.heart.exception.HeartNotFoundException;
import pagether.domain.note.domain.Note;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowResponse save(AddFollowRequest request, String userId) {
        if (request.getFolloweeId().equals(userId)){
            throw new FollowNotAllowedException();
        }
        User followee = userRepository.findByUserId(request.getFolloweeId()).orElseThrow(UserNotFountException::new);
        User follower = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        if (this.isFollowed(followee, follower)){
            throw new AlreadyFollowedAndNotFollowedException();
        }
        Follow follow = Follow.builder()
                .followee(followee)
                .follower(follower)
                .createdAt(LocalDateTime.now())
                .build();
        follow = followRepository.save(follow);
        return new FollowResponse(follow);
    }
    public FollowCountResponse getCount(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Long FolloweeCount = followRepository.countAllByFollowee(user);
        Long FollowerCount = followRepository.countAllByFollower(user);
        return new FollowCountResponse(FolloweeCount, FollowerCount);
    }

    public FollowListResponse getUsers(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Follow> followerList = followRepository.findAllByFollowee(user);
        List<Follow> followeeList = followRepository.findAllByFollower(user);

        return new FollowListResponse(followeeList, followerList);
    }

    public Boolean isFollowed(User followee, User follower) {
        return followRepository.existsByFolloweeAndFollower(followee, follower);
    }

    public void delete(String followeeId, String followerId) {
        User followee = userRepository.findByUserId(followeeId).orElseThrow(UserNotFountException::new);
        User follower = userRepository.findByUserId(followerId).orElseThrow(UserNotFountException::new);
        if (!this.isFollowed(followee, follower)){
            Follow follow = followRepository.findByFolloweeAndFollower(followee, follower).orElseThrow(FollowNotFoundException::new);
            followRepository.deleteById(follow.getFollowId());
        }
        throw new AlreadyFollowedAndNotFollowedException();
    }
}