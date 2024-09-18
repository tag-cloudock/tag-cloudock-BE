package pagether.domain.note.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.heart.repository.HeartRepository;
import pagether.domain.news.domain.News;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.domain.news.exception.NewsNotFoundException;
import pagether.domain.news.repository.NewsRepository;
import pagether.domain.note.domain.Note;
import pagether.domain.note.domain.NoteType;
import pagether.domain.note.dto.NoteDTO;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.dto.res.NoteResponse;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

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

    public NoteResponse save(AddNoteRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);
        Note note = Note.builder()
                .user(user)
                .book(book)
                .heartCount(0L)
                .rating(request.getType().equals(NoteType.REVIEW) ? request.getRating() : null)
                .topic(request.getType().equals(NoteType.DISCUSSION) ? request.getTopic() : null)
                .isPrivate(request.getIsPrivate())
                .hasSpoilerRisk(request.getHasSpoilerRisk())
                .type(request.getType())
                .content(request.getContent())
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
                    .hasSpoilerRisk(note.getHasSpoilerRisk())
                    .content(note.getContent())
                    .rating(note.getRating())
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
                    .content(note.getContent())
                    .hasSpoilerRisk(note.getHasSpoilerRisk())
                    .rating(note.getRating())
                    .isHeartClicked(isHeartClicked)
                    .heartCount(note.getHeartCount())
                    .type(note.getType())
                    .createdAt(LocalDateTime.now())
                    .build();
            responses.add(dto);
        }
        return responses;
    }

    public void delete(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new NoteNotFountException();
        }
        noteRepository.deleteById(noteId);
    }
}