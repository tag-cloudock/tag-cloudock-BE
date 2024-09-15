package pagether.domain.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.news.domain.News;
import pagether.domain.note.domain.Note;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> getNoteByNoteId(Long noteId);
}