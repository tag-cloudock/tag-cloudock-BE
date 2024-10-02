package pagether.domain.follow.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.domain.RequestStatus;
import pagether.domain.follow.dto.req.AddFollowRequest;
import pagether.domain.follow.dto.res.FollowCountResponse;
import pagether.domain.follow.dto.res.FollowListResponse;
import pagether.domain.follow.dto.res.FollowResponse;
import pagether.domain.follow.exception.AlreadyFollowedException;
import pagether.domain.follow.exception.FollowNotAllowedException;
import pagether.domain.follow.exception.FollowNotFoundException;
import pagether.domain.follow.repository.FollowRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AlertService alertService;

    public FollowResponse save(AddFollowRequest request, String userId) {
        if (request.getFolloweeId().equals(userId))
            throw new FollowNotAllowedException();

        User followee = userRepository.findByUserId(request.getFolloweeId()).orElseThrow(UserNotFountException::new);
        User follower = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);

        if (this.isFollowed(followee, follower))
            throw new AlreadyFollowedException();

        RequestStatus requestStatus = RequestStatus.ACCEPTED;
        if(followee.getIsAccountPrivate())
            requestStatus = RequestStatus.PENDING;

        Follow follow = Follow.builder()
                .followee(followee)
                .follower(follower)
                .requestStatus(requestStatus)
                .createdAt(LocalDateTime.now())
                .build();
        follow = followRepository.save(follow);
        alertService.createAlert(follower, followee, followee.getIsAccountPrivate() ? AlertType.FOLLOW_REQUEST : AlertType.FOLLOW, follow);
        return new FollowResponse(follow);
    }
    public FollowCountResponse getCount(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Long FolloweeCount = followRepository.countAllByFolloweeAndRequestStatus(user, RequestStatus.ACCEPTED);
        Long FollowerCount = followRepository.countAllByFollowerAndRequestStatus(user, RequestStatus.ACCEPTED);
        return new FollowCountResponse(FolloweeCount, FollowerCount);
    }

    public FollowListResponse getUsers(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Follow> followerList = followRepository.findAllByFolloweeAndRequestStatus(user, RequestStatus.ACCEPTED);
        List<Follow> followeeList = followRepository.findAllByFollowerAndRequestStatus(user, RequestStatus.ACCEPTED);
        return new FollowListResponse(followeeList, followerList);
    }

    public Boolean isFollowed(User followee, User follower) {
        return followRepository.existsByFolloweeAndFollower(followee, follower);
    }

    public void accept(Long followId) {
        Follow follow = followRepository.findById(followId).orElseThrow(FollowNotFoundException::new);
        follow.accept();
        followRepository.save(follow);
    }

    public void reject(Long followId) {
        followRepository.deleteById(followId);
    }

    public void delete(String followeeId, String followerId) {
        User followee = userRepository.findByUserId(followeeId).orElseThrow(UserNotFountException::new);
        User follower = userRepository.findByUserId(followerId).orElseThrow(UserNotFountException::new);
        Follow follow = followRepository.findByFolloweeAndFollower(followee, follower).orElseThrow(FollowNotFoundException::new);
        followRepository.deleteById(follow.getFollowId());
    }
}