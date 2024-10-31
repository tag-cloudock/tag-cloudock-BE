package gachonherald.domain.book.dto.res;

import lombok.*;
import gachonherald.domain.book.domain.Book;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookResponse {
    private String bookId;
    private LocalDateTime createdAt;

    public BookResponse(Book book) {
        bookId = book.getIsbn();
        createdAt = book.getCreatedAt();
    }
}
