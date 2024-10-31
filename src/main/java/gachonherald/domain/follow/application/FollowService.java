package gachonherald.domain.follow.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.alert.application.AlertService;
import gachonherald.domain.alert.domain.AlertType;
import gachonherald.domain.block.exception.UserBlockedException;
import gachonherald.domain.block.exception.UserBlockingException;
import gachonherald.domain.checker.application.CheckerService;
import gachonherald.domain.follow.domain.FetchFollowType;
import gachonherald.domain.follow.domain.Follow;
import gachonherald.domain.follow.domain.RequestStatus;
import gachonherald.domain.follow.dto.FollowDTO;
import gachonherald.domain.follow.dto.req.AddFollowRequest;
import gachonherald.domain.follow.dto.res.FollowCountResponse;
import gachonherald.domain.follow.dto.res.FollowListResponse;
import gachonherald.domain.follow.dto.res.FollowResponse;
import gachonherald.domain.follow.exception.AlreadyFollowedException;
import gachonherald.domain.follow.exception.FollowNotAllowedException;
import gachonherald.domain.follow.exception.FollowNotFoundException;
import gachonherald.domain.follow.repository.FollowRepository;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;
import gachonherald.global.config.exception.IllegalArgumentException;

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
    private final CheckerService checkerService;
    public static final int PAGE_SIZE = 10;
    private static final Pageable PAGEABLE = PageRequest.of(0, PAGE_SIZE);

    public FollowResponse save(AddFollowRequest request, String userId) {
        User followee = userRepository.findByUserId(request.getFolloweeId()).orElseThrow(UserNotFountException::new);
        User follower = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);

        checkSelfFollowAttempt(request.getFolloweeId(),userId);
        checkAlreadyFollowed(followee, follower);
        checkBlock(followee, follower);

        RequestStatus requestStatus = RequestStatus.ACCEPTED;
        if(followee.getIsAccountPrivate()) requestStatus = RequestStatus.PENDING;

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

    public FollowListResponse getList(FetchFollowType type, Long cursor, String userId) {
        if (type.equals(FetchFollowType.FOLLOWER)){
            return getFollowerList(cursor, userId);
        } else if (type.equals(FetchFollowType.FOLLOWING)){
            return getFollowingList(cursor, userId);
        }
        throw new IllegalArgumentException();
    }

    public FollowListResponse getFollowingList(Long cursor, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Follow> followingList = followRepository.findAllByFollowerAndRequestStatusAndFollowIdLessThan(user, RequestStatus.ACCEPTED, cursor, PAGEABLE);
        checkerService.checkLastPage(followingList);
        List<FollowDTO> dtos = new ArrayList<>();
        for (Follow follow : followingList) dtos.add(new FollowDTO(follow, follow.getFollowee()));
        return new FollowListResponse(dtos, followingList.get(followingList.size()-1).getFollowId());
    }

    public FollowListResponse getFollowerList(Long cursor, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Follow> followerList = followRepository.findAllByFolloweeAndRequestStatusAndFollowIdLessThan(user, RequestStatus.ACCEPTED, cursor, PAGEABLE);
        checkerService.checkLastPage(followerList);
        List<FollowDTO> dtos = new ArrayList<>();
        for (Follow follow : followerList) dtos.add(new FollowDTO(follow, follow.getFollower()));
        return new FollowListResponse(dtos, followerList.get(followerList.size()-1).getFollowId());
    }

    public void checkBlock(User followee, User follower) {
        if (checkerService.isBlocked(follower, followee))
            throw new UserBlockedException();
        if (checkerService.isBlocked(followee, follower))
            throw new UserBlockingException();
    }

    public Boolean isFollowed(User followee, User follower) {
        return followRepository.existsByFolloweeAndFollower(followee, follower);
    }

    public void checkAlreadyFollowed(User followee, User follower) {
        if (followRepository.existsByFolloweeAndFollower(followee, follower))
            throw new AlreadyFollowedException();
    }

    public void checkSelfFollowAttempt(String followeeId, String requesterId) {
        if (followeeId.equals(requesterId))
            throw new FollowNotAllowedException();
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