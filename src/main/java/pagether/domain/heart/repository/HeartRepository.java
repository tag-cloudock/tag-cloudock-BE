package pagether.domain.heart.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.follow.domain.Follow;
import pagether.domain.heart.domain.Heart;
import pagether.domain.note.domain.Note;
import pagether.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface HeartRepository extends JpaRepository<Heart, Long> {
    Long countAllByNote(Note note);
    Boolean existsByNoteAndHeartClicker(Note note, User heartClicker);
    Optional<Heart> findByNoteAndHeartClicker(Note note, User heartClicker);

    List<Heart> findAllByHeartClickerAndHeartIdLessThanOrderByHeartIdDesc(User user, Long heartId, Pageable pageable);
}
