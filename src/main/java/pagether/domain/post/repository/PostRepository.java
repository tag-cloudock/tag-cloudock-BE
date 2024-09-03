package pagether.domain.post.repository;

import pagether.domain.post.domain.Post;
import pagether.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findTop3ByUserOrderByCreatedAtDesc(User user);

    Boolean existsByPostId(Integer postId);

    List<Post> findAllByLocationOrderByIsCloseAscCreatedAtDesc(String location, Pageable pageable);

    Optional<Post> findByPostId(Integer postId);

    void deleteByPostId(Integer postId);

    List<Post> findTop8ByLocationStartingWithOrderByCreatedAtDesc(String prefix);

    List<Post> findAllByLocationStartingWithOrderByCreatedAtDesc(String prefix, Pageable pageable);

    Long countAllByLocationStartingWith(String prefix);

    Long countAllByLocation(String location);
}
