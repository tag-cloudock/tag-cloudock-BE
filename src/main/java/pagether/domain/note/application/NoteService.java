package pagether.domain.note.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.news.domain.News;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.domain.news.exception.NewsNotFoundException;
import pagether.domain.news.repository.NewsRepository;
import pagether.domain.note.domain.Note;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.dto.res.NoteResponse;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoteService {

    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;

    public NoteResponse save(AddNoteRequest request) {
        Book book = bookRepository.findByBookId(request.getBookId()).orElseThrow(BookNotFoundException::new);
        Note note = Note.builder()
                .book(book)
                .likeCount(0L)
                .rating(0)
                .isPrivate(request.getIsPrivate())
                .type(request.getType())
                .content(request.getContent())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        note = noteRepository.save(note);
        return new NoteResponse(note);
    }

    public void delete(Long noteId) {
        if (!noteRepository.existsById(noteId)) {
            throw new NoteNotFountException();
        }
        noteRepository.deleteById(noteId);
    }
}