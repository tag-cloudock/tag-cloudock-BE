package gachonherald.domain.book.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class AddBookRequest {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Long pageCount;
    private Long categoryId;
    private String description;
    private String coverImgName;
    private Integer weight;
    private Integer sizeDepth;
    private Integer sizeHeight;
    private Integer sizeWidth;
}