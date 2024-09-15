package pagether.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.alert.domain.Alert;
import pagether.domain.follow.domain.Follow;
import pagether.domain.user.domain.User;

import java.util.List;

@Repository
@Transactional
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow>  findAllByFollower(User follower);
    List<Follow>  findAllByFollowee(User follower);
    Long countAllByFollower(User follower);
    Long countAllByFollowee(User follower);
}
