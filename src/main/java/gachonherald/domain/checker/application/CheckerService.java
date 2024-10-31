package gachonherald.domain.checker.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.block.repository.BlockRepository;
import gachonherald.domain.follow.repository.FollowRepository;
import gachonherald.domain.user.domain.User;
import gachonherald.global.config.exception.LastPageReachedException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class CheckerService {

    private final FollowRepository followRepository;
    private final BlockRepository blockRepository;
    public Boolean isFollowed(User followee, User follower) {
        return followRepository.existsByFolloweeAndFollower(followee, follower);
    }
    public Boolean isBlocked(User blocked, User blocking) {
        return blockRepository.existsByBlockedAndBlocking(blocked, blocking);
    }

    public void checkLastPage(List<?> datas) {
        if (datas.isEmpty()) throw new LastPageReachedException();
    }
}