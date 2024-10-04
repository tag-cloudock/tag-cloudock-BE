package pagether.domain.follow.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.block.application.BlockService;
import pagether.domain.block.exception.UserBlockedException;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.domain.RequestStatus;
import pagether.domain.follow.dto.FollowDTO;
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
import pagether.global.config.exception.LastPageReachedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AlertService alertService;
    private final BlockService blockService;
    public static final int PAGE_SIZE = 10;

    public FollowResponse save(AddFollowRequest request, String userId) {
        if (request.getFolloweeId().equals(userId))
            throw new FollowNotAllowedException();

        User followee = userRepository.findByUserId(request.getFolloweeId()).orElseThrow(UserNotFountException::new);
        User follower = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);

        if (this.isFollowed(followee, follower))
            throw new AlreadyFollowedException();
        if (blockService.isBlocked(follower, followee))
            throw new UserBlockedException();

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

    public FollowListResponse getFollowingList(String userId, Long cursor) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        List<Follow> followingList = followRepository.findAllByFollowerAndRequestStatusAndFollowIdLessThan(user, RequestStatus.ACCEPTED, cursor, pageable);
        if (followingList.isEmpty())
            throw new LastPageReachedException();

        List<FollowDTO> dtos = new ArrayList<>();
        for (Follow follow : followingList)
            dtos.add(new FollowDTO(follow, follow.getFollowee()));
        return new FollowListResponse(dtos, followingList.get(followingList.size()-1).getFollowId());
    }

    public FollowListResponse getFollowerList(String userId, Long cursor) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        List<Follow> followerList = followRepository.findAllByFolloweeAndRequestStatusAndFollowIdLessThan(user, RequestStatus.ACCEPTED, cursor, pageable);
        if (followerList.isEmpty())
            throw new LastPageReachedException();

        List<FollowDTO> dtos = new ArrayList<>();
        for (Follow follow : followerList)
            dtos.add(new FollowDTO(follow, follow.getFollower()));
        return new FollowListResponse(dtos, followerList.get(followerList.size()-1).getFollowId());
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