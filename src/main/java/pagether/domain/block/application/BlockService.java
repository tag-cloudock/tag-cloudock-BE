package pagether.domain.block.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.block.domain.Block;
import pagether.domain.block.dto.BlockDTO;
import pagether.domain.block.dto.req.AddBlockRequest;
import pagether.domain.block.dto.res.BlockCountResponse;
import pagether.domain.block.dto.res.BlockListResponse;
import pagether.domain.block.dto.res.BlockResponse;
import pagether.domain.block.exception.AlreadyBlockedException;
import pagether.domain.block.exception.BlockNotAllowedException;
import pagether.domain.block.exception.UserBlockedException;
import pagether.domain.block.repository.BlockRepository;
import pagether.domain.checker.application.CheckerService;
import pagether.domain.follow.application.FollowService;
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
public class BlockService {

    private final BlockRepository blockRepository;
    private final UserRepository userRepository;
    private final CheckerService checkerService;
    private final FollowService followService;
    public static final int PAGE_SIZE = 10;
    private static final Pageable PAGEABLE = PageRequest.of(0, PAGE_SIZE);

    public BlockResponse save(AddBlockRequest request, String userId) {
        User blocked = userRepository.findByUserId(request.getBlockedUserId()).orElseThrow(UserNotFountException::new);
        User blocking = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);

        checkSelfBlockAttempt(request.getBlockedUserId(), userId);
        checkAlreadyBlocked(blocked, blocking);
        checkAndUnfollow(blocked, blocking);

        Block block = Block.builder()
                .blocked(blocked)
                .blocking(blocking)
                .createdAt(LocalDateTime.now())
                .build();
        block = blockRepository.save(block);
        return new BlockResponse(block);
    }
    public BlockCountResponse getCount(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Long BlockCount = blockRepository.countAllByBlocking(user);
        return new BlockCountResponse(BlockCount);
    }

    public BlockListResponse getBlockedList(String userId, Long cursor) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Block> blockList = blockRepository.findAllByBlockingAndBlockIdLessThan(user, cursor, PAGEABLE);
        checkerService.checkLastPage(blockList);
        List<BlockDTO> dtos = new ArrayList<>();
        for (Block block : blockList) dtos.add(new BlockDTO(block));
        return new BlockListResponse(dtos, blockList.get(blockList.size()-1).getBlockId());
    }

    public void checkAndUnfollow(User blocked, User blocking) {
        if (checkerService.isFollowed(blocking, blocked))
            followService.delete(blocking.getId(), blocked.getId());
        if (checkerService.isFollowed(blocked, blocking))
            followService.delete(blocked.getId(), blocking.getId());
    }

    public void checkAlreadyBlocked(User blocked, User blocking) {
        if (blockRepository.existsByBlockedAndBlocking(blocked, blocking))
            throw new AlreadyBlockedException();
    }

    public Boolean isBlocked(User blocked, User blocking) {
        return blockRepository.existsByBlockedAndBlocking(blocked, blocking);
    }

    public void checkUserBlock(User blocked, User blocking) {
        if (blockRepository.existsByBlockedAndBlocking(blocked, blocking))
            throw new UserBlockedException();
    }

    public void checkSelfBlockAttempt(String blockedUserId, String requesterId) {
        if (blockedUserId.equals(requesterId))
            throw new BlockNotAllowedException();
    }

    public void delete(String blockedUserId, String blockingUserId) {
        User blocked = userRepository.findByUserId(blockedUserId).orElseThrow(UserNotFountException::new);
        User blocking = userRepository.findByUserId(blockingUserId).orElseThrow(UserNotFountException::new);
        Block block = blockRepository.findByBlockedAndBlocking(blocked, blocking).orElseThrow(FollowNotFoundException::new);
        blockRepository.deleteById(block.getBlockId());
    }
}