package pagether.domain.block.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.block.domain.Block;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.domain.RequestStatus;
import pagether.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BlockRepository extends JpaRepository<Block, Long> {
    List<Block>  findAllByBlockingAndBlockIdLessThan(User blocking, Long blockId, Pageable pageable);
    Optional<Block> findByBlockedAndBlocking(User blocked, User blocking);
    Long countAllByBlocking(User blocking);
    Boolean existsByBlockedAndBlocking(User blocked, User blocking);
}
