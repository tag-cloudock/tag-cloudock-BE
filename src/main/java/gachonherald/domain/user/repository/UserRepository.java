package gachonherald.domain.user.repository;

import org.springframework.data.domain.Pageable;
import gachonherald.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Boolean existsUserByUserId(String userId);
    List<User> findAllByNickNameContaining(String keyword, Pageable pageable);

    List<User> findAllByIsCurrentMember(Boolean isCurrentMember);
}