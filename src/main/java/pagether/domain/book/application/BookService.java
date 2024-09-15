package pagether.domain.book.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.book.dto.req.AddBookRequest;
import pagether.domain.book.dto.res.BookResponse;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.news.domain.News;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.domain.news.exception.NewsNotFoundException;
import pagether.domain.news.repository.NewsRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse save(AddBookRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .pageCount(request.getPageCount())
                .coverImgName(request.getCoverImgName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        book = bookRepository.save(book);
        return new BookResponse(book);
    }

    public void delete(Long newsId) {
        if (!bookRepository.existsById(newsId)) {
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(newsId);
    }
}