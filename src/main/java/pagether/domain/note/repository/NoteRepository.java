package pagether.domain.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.note.domain.Note;
import pagether.domain.note.domain.NoteType;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.user.domain.User;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserAndCreatedAtAfterOrderByCreatedAtDesc(User user, LocalDateTime date);
    List<Note> findAllByCreatedAtAfterOrderByHeartCountDesc(LocalDateTime date);
    List<Note> findAllByUserAndTypeOrderByCreatedAtDesc(User user, NoteType noteType);
    List<Note> findAllByUserAndTypeAndIsPrivateOrderByCreatedAtDesc(User user, NoteType noteType, Boolean isPrivate);
    List<Note> findAllByBookAndTypeAndIsPrivateOrderByCreatedAtDesc(Book book, NoteType noteType, Boolean isPrivate);
    Optional<Note> findByReadInfoAndType(ReadInfo readInfo, NoteType noteType);
    List<Note> findAllByReadInfo(ReadInfo readInfo);
}