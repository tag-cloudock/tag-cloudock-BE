package pagether.domain.readInfo.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pagether.domain.readInfo.dto.BookImgDTO;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class ReadingAndReadBooksResponse {
    private List<BookImgDTO> readingBooks;
    private List<BookImgDTO> readBooks;

    @Builder
    public ReadingAndReadBooksResponse(List<BookImgDTO> readingBooks, List<BookImgDTO> readBooks) {
        this.readingBooks = readingBooks;
        this.readBooks = readBooks;
    }
}
