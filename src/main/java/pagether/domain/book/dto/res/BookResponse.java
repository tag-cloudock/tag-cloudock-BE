package pagether.domain.book.dto.res;

import lombok.*;
import pagether.domain.alert.domain.Alert;
import pagether.domain.alert.domain.AlertType;
import pagether.domain.book.domain.Book;
import pagether.domain.note.domain.Note;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponse {
    private String bookId;
    private LocalDateTime createdAt;

    @Builder
    public BookResponse(Book book) {
        bookId = book.getIsbn();
        createdAt = book.getCreatedAt();
    }
}
