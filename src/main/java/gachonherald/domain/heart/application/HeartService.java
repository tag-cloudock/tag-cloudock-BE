package gachonherald.domain.heart.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.alert.application.AlertService;
import gachonherald.domain.alert.domain.AlertType;
import gachonherald.domain.heart.domain.Heart;
import gachonherald.domain.heart.dto.req.AddHeartRequest;
import gachonherald.domain.heart.dto.res.HeartResponse;
import gachonherald.domain.heart.exception.HeartNotFoundException;
import gachonherald.domain.heart.repository.HeartRepository;
import gachonherald.domain.note.application.NoteService;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.note.exception.NoteNotFountException;
import gachonherald.domain.note.repository.NoteRepository;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;

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
        noteService.checkHeartAlreadyClicked(note, heartClicker);
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
        if (!noteService.isHeartClicked(note, user))
            throw new HeartNotFoundException();
        Heart heart = heartRepository.findByNoteAndHeartClicker(note, user).orElseThrow(HeartNotFoundException::new);
        noteService.decrementHeartCount(note);
        heartRepository.deleteById(heart.getHeartId());
    }
}