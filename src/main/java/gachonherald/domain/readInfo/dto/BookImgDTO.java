package gachonherald.domain.readInfo.dto;

import lombok.*;
import gachonherald.domain.readInfo.domain.ReadInfo;


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
