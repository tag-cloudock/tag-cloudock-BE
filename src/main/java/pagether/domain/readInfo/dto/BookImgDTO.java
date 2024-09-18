package pagether.domain.readInfo.dto;

import lombok.*;
import pagether.domain.readInfo.domain.ReadInfo;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookImgDTO {
    private String isbn;
    private String bookCoverImgName;

    @Builder
    public BookImgDTO(ReadInfo readInfo) {
        isbn = readInfo.getBook().getIsbn();
        bookCoverImgName = readInfo.getBook().getCoverImgName();
    }
}
