package pagether.domain.readInfo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.readInfo.dto.req.AddReadInfoRequest;
import pagether.domain.readInfo.dto.req.UpdatePageRequest;
import pagether.domain.readInfo.dto.res.ReadInfoResponse;
import pagether.domain.readInfo.exception.ReadInfoNotFountException;
import pagether.domain.readInfo.exception.UnauthorizedAccessException;
import pagether.domain.readInfo.repository.ReadRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReadInfoService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReadRepository readRepository;

    public ReadInfoResponse startRead(AddReadInfoRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);

        if (readRepository.existsByBookAndUserAndToReadLater(book, user, true)){
            ReadInfo readInfo = readRepository.findByBookAndUserAndToReadLater(book, user, true).orElseThrow(ReadInfoNotFountException::new);
            readInfo.start();
            readRepository.save(readInfo);
            return new ReadInfoResponse(readInfo);
        }

        ReadInfo readInfo = ReadInfo.builder()
                .book(book)
                .user(user)
                .readCount(0L)
                .toReadLater(false)
                .isCompleted(false)
                .hasReview(false)
                .startDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        readInfo = readRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse pin(AddReadInfoRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);
        ReadInfo readInfo = ReadInfo.builder()
                .book(book)
                .user(user)
                .readCount(0L)
                .toReadLater(true)
                .isCompleted(false)
                .hasReview(false)
                .startDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        readInfo = readRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse pageUpdate(UpdatePageRequest request, String userId) {
        ReadInfo readInfo = readRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        if (!readInfo.getUser().getUserId().equals(userId)){
            throw new UnauthorizedAccessException();
        }
        readInfo.setCurrentPage(request.getPage());
        readInfo = readRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public void delete(Long readId) {
        if (!bookRepository.existsById(readId)) {
            throw new ReadInfoNotFountException();
        }
        bookRepository.deleteById(readId);
    }
}