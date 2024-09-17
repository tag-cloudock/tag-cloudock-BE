package pagether.domain.book.dto.req;

import jakarta.persistence.Column;
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
}