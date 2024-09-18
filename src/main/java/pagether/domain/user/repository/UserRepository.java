package pagether.domain.user.repository;

import org.springframework.data.domain.Pageable;
import pagether.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Optional<User> findById(String id);

    Boolean existsUserByUserId(String userId);

    Boolean existsUserById(String id);

    List<User> findAllByNickNameContaining(String keyword, Pageable pageable);
}