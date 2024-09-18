package pagether.domain.readInfo.dto.res;

import lombok.*;
import pagether.domain.readInfo.dto.BookImgDTO;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class ReadingAndPinedBooksResponse {
    private List<BookImgDTO> readingBooks;
    private List<BookImgDTO> pinedBooks;

    @Builder
    public ReadingAndPinedBooksResponse(List<BookImgDTO> readingBooks, List<BookImgDTO> pinedBooks) {
        this.readingBooks = readingBooks;
        this.pinedBooks = pinedBooks;
    }
}
