package pagether.domain.heart.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.heart.domain.Heart;
import pagether.domain.heart.dto.req.AddHeartRequest;
import pagether.domain.heart.dto.res.HeartResponse;
import pagether.domain.heart.exception.AlreadyClickedAndNotClickedException;
import pagether.domain.heart.exception.HeartNotFoundException;
import pagether.domain.heart.repository.HeartRepository;
import pagether.domain.note.domain.Note;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;

    public HeartResponse save(AddHeartRequest request, String userId) {
        User heartClicker = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Note note = noteRepository.findById(request.getNoteId()).orElseThrow(NoteNotFountException::new);
        if (this.isClicked(note, userId)){
            throw new AlreadyClickedAndNotClickedException();
        }
        Heart heart = Heart.builder()
                .heartClicker(heartClicker)
                .note(note)
                .createdAt(LocalDateTime.now())
                .build();

        note.incrementHeartCount();
        noteRepository.save(note);
        heart = heartRepository.save(heart);
        return new HeartResponse(heart);
    }
    public Long getCountByNote(Note note) {
        return heartRepository.countAllByNote(note);
    }

    public Boolean isClicked(Note note, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        return heartRepository.existsByNoteAndHeartClicker(note, user);
    }

    public void delete(Long noteId, String userId) {
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        if (this.isClicked(note, userId)) {
            Heart heart = heartRepository.findByNoteAndHeartClicker(note, user).orElseThrow(HeartNotFoundException::new);
            note.decrementHeartCount();
            noteRepository.save(note);
            heartRepository.deleteById(heart.getHeartId());
        }
        throw new AlreadyClickedAndNotClickedException();
    }
}