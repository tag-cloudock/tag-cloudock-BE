package pagether.domain.note.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.book.domain.Book;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.heart.application.HeartService;
import pagether.domain.heart.domain.Heart;
import pagether.domain.heart.repository.HeartRepository;
import pagether.domain.image.application.ImageService;
import pagether.domain.note.domain.Note;
import pagether.domain.note.domain.NoteType;
import pagether.domain.note.dto.CommentDTO;
import pagether.domain.note.dto.NoteDTO;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.dto.req.UpdateNoteRequest;
import pagether.domain.note.dto.res.NoteContentResponse;
import pagether.domain.note.dto.res.NoteResponse;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.note.exception.ReviewNotAllowedException;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.readInfo.domain.ReadStatus;
import pagether.domain.readInfo.exception.ReadInfoNotFountException;
import pagether.domain.readInfo.repository.ReadInfoRepository;
import pagether.domain.user.application.UserService;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import pagether.global.config.exception.UnauthorizedAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoteService {

    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;
    private final ReadInfoRepository readInfoRepository;
    private final AlertService alertService;
    private final ImageService imageService;
    private final UserService userService;

    public NoteResponse save(AddNoteRequest request, String userId,  MultipartFile pic) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);
        ReadInfo lastReadInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, user, true).orElseThrow(ReadInfoNotFountException::new);

        Note discussion = null;
        if (request.getType().equals(NoteType.REVIEW)){
            if (!(lastReadInfo.getReadStatus().equals(ReadStatus.READ) || lastReadInfo.getReadStatus().equals(ReadStatus.STOPPED)))
                throw new ReviewNotAllowedException();
            if (lastReadInfo.getHasReview())
                throw new ReviewNotAllowedException();
        } else if (request.getType().equals(NoteType.COMMENT)){
            discussion = noteRepository.findById(request.getDiscussionId()).orElseThrow(NoteNotFountException::new);
        }

        String imgName = imageService.save(pic, true);
        Note note = Note.builder()
                .user(user)
                .book(book)
                .imgName(imgName)
                .heartCount(0L)
                .readInfo(lastReadInfo)
                .discussion(request.getType().equals(NoteType.COMMENT) ? discussion : null)
                .rating(request.getType().equals(NoteType.REVIEW) ? request.getRating() : null)
                .topic(request.getType().equals(NoteType.DISCUSSION) ? request.getTopic() : null)
                .sentence(request.getType().equals(NoteType.SENTENCE) ? request.getSentence() : null)
                .isPrivate(!request.getType().equals(NoteType.DISCUSSION) && request.getIsPrivate())
                .hasSpoilerRisk(request.getHasSpoilerRisk())
                .type(request.getType())
                .content(request.getContent())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        note = noteRepository.save(note);
        if (request.getType().equals(NoteType.COMMENT))
            alertService.createAlert(user, discussion.getUser(), AlertType.COMMENT, discussion);
        return new NoteResponse(note);
    }
    public void update(UpdateNoteRequest request, Long noteId, String userId) {
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
        if (!note.getUser().getUserId().equals(userId))
            throw new UnauthorizedAccessException();
        note.setContent(request.getContent());
        note.setRating(request.getRating());
        note.setTopic(request.getTopic());
        note.setSentence(request.getSentence());
        note.setIsPrivate(request.getIsPrivate());
        note.setHasSpoilerRisk(request.getHasSpoilerRisk());
        note.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    public NoteContentResponse get(Long noteId, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
        if (!note.getUser().getUserId().equals(userId)){
            throw new UnauthorizedAccessException();
        }
        return new NoteContentResponse(note, this.isClicked(note, user));
    }

    public List<CommentDTO> getComment(Long discussionId, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Note discussion = noteRepository.findById(discussionId).orElseThrow(NoteNotFountException::new);
        List<Note> comment = noteRepository.findAllByDiscussion(discussion);
        List<CommentDTO> dtos = new ArrayList<>();
        for (Note note : comment){
            CommentDTO dto = new CommentDTO(note, this.isClicked(note, user));
            dtos.add(dto);
        }
        return dtos;
    }

    public NoteResponse createStartNote(User user, Book book) {
        Note note = Note.builder()
                .user(user)
                .book(book)
                .heartCount(0L)
                .rating(null)
                .topic(null)
                .isPrivate(false)
                .hasSpoilerRisk(false)
                .type(NoteType.NEW)
                .content("")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        note = noteRepository.save(note);
        return new NoteResponse(note);
    }

    public List<NoteDTO> getNotesByUser(String type, String noteUserId, String userId) {
        List<NoteDTO> responses = new ArrayList<>();
        User noteUser = userRepository.findByUserId(noteUserId).orElseThrow(UserNotFountException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        NoteType noteType = NoteType.fromString(type);
        List<Note> notes;
        if (noteUserId.equals(userId)){
            notes = noteRepository.findAllByUserAndTypeOrderByCreatedAtDesc(noteUser, noteType);
        }else {
            notes = noteRepository.findAllByUserAndTypeAndIsPrivateOrderByCreatedAtDesc(noteUser, noteType, false);
        }
        for(Note note : notes){
            NoteDTO dto = new NoteDTO(note, this.isClicked(note, user));
            responses.add(dto);
        }
        return responses;
    }

    public List<NoteDTO> getNotesByBook(String type, String isbn, String userId) {
        List<NoteDTO> responses = new ArrayList<>();
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        NoteType noteType = NoteType.fromString(type);
        List<Note> notes = noteRepository.findAllByBookAndTypeAndIsPrivateOrderByCreatedAtDesc(book, noteType, false);
        for(Note note : notes){
            NoteDTO dto = new NoteDTO(note, this.isClicked(note, user));
            responses.add(dto);
        }
        return responses;
    }

    public List<NoteDTO> getHeartedNotes(String userId) {
        List<NoteDTO> responses = new ArrayList<>();
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Pageable pageable = PageRequest.of(0, 5);
        List<Heart> hearts = heartRepository.findAllByHeartClickerOrderByCreatedAtDesc(user, pageable);
        for(Heart heart : hearts){
            Note note = heart.getNote();
            NoteDTO dto = new NoteDTO(note, true);
            responses.add(dto);
        }
        return responses;
    }

    public void incrementHeartCount(Note note) {
        note.incrementHeartCount();
        noteRepository.save(note);
    }

    public void decrementHeartCount(Note note) {
        note.decrementHeartCount();
        noteRepository.save(note);
    }

    public Boolean isClicked(Note note, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        return heartRepository.existsByNoteAndHeartClicker(note, user);
    }

    public Boolean isClicked(Note note, User user) {
        return heartRepository.existsByNoteAndHeartClicker(note, user);
    }


    public void delete(Long noteId, String userId) {
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
        userService.validateOwnership(note.getUser().getUserId(),userId);
        noteRepository.deleteById(noteId);
    }
}