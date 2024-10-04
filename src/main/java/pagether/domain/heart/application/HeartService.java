package pagether.domain.heart.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.heart.domain.Heart;
import pagether.domain.heart.dto.req.AddHeartRequest;
import pagether.domain.heart.dto.res.HeartResponse;
import pagether.domain.heart.exception.AlreadyClickedException;
import pagether.domain.heart.exception.HeartNotFoundException;
import pagether.domain.heart.repository.HeartRepository;
import pagether.domain.note.application.NoteService;
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
    private final AlertService alertService;
    private final NoteService noteService;

    public HeartResponse save(AddHeartRequest request, String userId) {
        User heartClicker = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Note note = noteRepository.findById(request.getNoteId()).orElseThrow(NoteNotFountException::new);
        if (noteService.isHeartClicked(note, userId))
            throw new AlreadyClickedException();

        Heart heart = Heart.builder()
                .heartClicker(heartClicker)
                .note(note)
                .createdAt(LocalDateTime.now())
                .build();
        noteService.incrementHeartCount(note);
        heart = heartRepository.save(heart);
        alertService.createAlert(heartClicker, note.getUser(), AlertType.HEART, note);
        return new HeartResponse(heart);
    }

    public void delete(Long noteId, String userId) {
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        if (noteService.isHeartClicked(note, userId)) {
            Heart heart = heartRepository.findByNoteAndHeartClicker(note, user).orElseThrow(HeartNotFoundException::new);
            noteService.decrementHeartCount(note);
            heartRepository.deleteById(heart.getHeartId());
        }
        throw new AlreadyClickedException();
    }
}