package gachonherald.domain.follow.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.follow.domain.Follow;
import gachonherald.domain.follow.domain.RequestStatus;
import gachonherald.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowerAndRequestStatus(User follower, RequestStatus requestStatus);
    List<Follow>  findAllByFollowerAndRequestStatusAndFollowIdLessThan(User follower, RequestStatus requestStatus, Long followId, Pageable pageable);
    List<Follow>  findAllByFolloweeAndRequestStatusAndFollowIdLessThan(User follower, RequestStatus requestStatus, Long followId, Pageable pageable);
    Optional<Follow> findByFolloweeAndFollower(User followee, User follower);
    Long countAllByFollowerAndRequestStatus(User follower, RequestStatus requestStatus);
    Long countAllByFolloweeAndRequestStatus(User follower, RequestStatus requestStatus);

    Boolean existsByFolloweeAndFollower(User followee, User follower);
}
