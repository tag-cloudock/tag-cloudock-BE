package pagether.domain.note.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.book.domain.Book;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.heart.repository.HeartRepository;
import pagether.domain.image.application.ImageService;
import pagether.domain.news.domain.News;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.domain.news.exception.NewsNotFoundException;
import pagether.domain.news.repository.NewsRepository;
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
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import pagether.global.config.exception.UnauthorizedAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public NoteResponse save(AddNoteRequest request, String userId,  MultipartFile pic) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);
        ReadInfo lastReadInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, user, true).orElseThrow(ReadInfoNotFountException::new);
        String imgName = null;
        if (pic != null) {
            imgName = imageService.save(pic);
        }
        if (request.getType().equals(NoteType.REVIEW)){
            if (!(lastReadInfo.getReadStatus().equals(ReadStatus.READ) || lastReadInfo.getReadStatus().equals(ReadStatus.STOPPED))){
                throw new ReviewNotAllowedException();
            }
            if (lastReadInfo.getHasReview()){
                throw new ReviewNotAllowedException();
            }
        }
        Note discussion = null;
        if (request.getType().equals(NoteType.COMMENT)){
            discussion = noteRepository.findById(request.getDiscussionId()).orElseThrow(NoteNotFountException::new);
        }
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
        if (request.getType().equals(NoteType.COMMENT)){
            alertService.createAlert(user, discussion.getUser(), AlertType.COMMENT, discussion);
        }
        return new NoteResponse(note);
    }
    public void update(UpdateNoteRequest request, Long noteId, String userId) {
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
        if (!note.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedAccessException();
        }
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
        if (note.getUser().getUserId().equals(userId)){
            Boolean isHeartClicked = heartRepository.existsByNoteAndHeartClicker(note, user);
            return new NoteContentResponse(note, isHeartClicked);
        }
        throw new UnauthorizedAccessException();
    }

    public List<CommentDTO> getComment(Long discussionId, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Note discussion = noteRepository.findById(discussionId).orElseThrow(NoteNotFountException::new);
        List<Note> comment = noteRepository.findAllByDiscussion(discussion);
        List<CommentDTO> dtos = new ArrayList<>();

        for (Note note : comment){
            Boolean isHeartClicked = heartRepository.existsByNoteAndHeartClicker(note, user);
            CommentDTO dto = CommentDTO.builder()
                    .userName(note.getUser().getNickName())
                    .userProfileImgName(note.getUser().getImgPath())
                    .noteId(note.getNoteId())
                    .content(note.getContent())
                    .isHeartClicked(isHeartClicked)
                    .heartCount(note.getHeartCount())
                    .type(note.getType())
                    .createdAt(LocalDateTime.now())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    public NoteResponse newBook(User user, Book book) {
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
            Boolean isHeartClicked = heartRepository.existsByNoteAndHeartClicker(note, user);
            NoteDTO dto = NoteDTO.builder()
                    .userName(note.getUser().getNickName())
                    .userProfileImgName(note.getUser().getImgPath())
                    .noteId(note.getNoteId())
                    .imgName(note.getImgName())
                    .hasSpoilerRisk(note.getHasSpoilerRisk())
                    .content(note.getContent())
                    .rating(note.getRating())
                    .sentence(note.getSentence())
                    .topic(note.getTopic())
                    .isPrivate(note.getIsPrivate())
                    .isHeartClicked(isHeartClicked)
                    .heartCount(note.getHeartCount())
                    .bookName(note.getBook().getTitle())
                    .isbn(note.getBook().getIsbn())
                    .type(note.getType())
                    .createdAt(LocalDateTime.now())
                    .build();
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
            Boolean isHeartClicked = heartRepository.existsByNoteAndHeartClicker(note, user);
            NoteDTO dto = NoteDTO.builder()
                    .userName(note.getUser().getNickName())
                    .userProfileImgName(note.getUser().getImgPath())
                    .noteId(note.getNoteId())
                    .imgName(note.getImgName())
                    .content(note.getContent())
                    .hasSpoilerRisk(note.getHasSpoilerRisk())
                    .rating(note.getRating())
                    .sentence(note.getSentence())
                    .topic(note.getTopic())
                    .isHeartClicked(isHeartClicked)
                    .heartCount(note.getHeartCount())
                    .bookName(note.getBook().getTitle())
                    .type(note.getType())
                    .createdAt(LocalDateTime.now())
                    .build();
            responses.add(dto);
        }
        return responses;
    }

    public void delete(Long noteId, String userId) {
        Note note = noteRepository.findById(noteId).orElseThrow(NoteNotFountException::new);
        if (!noteRepository.existsById(noteId)) {
            throw new NoteNotFountException();
        }
        if (!note.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedAccessException();
        }
        noteRepository.deleteById(noteId);
    }
}