package pagether.domain.feed.dto.res;

import lombok.*;
import pagether.domain.book.domain.Book;
import pagether.domain.feed.domain.FeedType;
import pagether.domain.note.domain.Note;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FeedDTO {
    private Long id;
    private FeedType type;
    private String userProfileImgName;
    private String userId;
    private String userName;
    private String bookCoverImgName;
    private String bookName;
    private String content;
    private LocalDateTime createdAt;
    private Long heartCount;
    private Boolean isHeartClicked;
    private String isbn;

    public FeedDTO(Note note, Long heartCount, Boolean isHeartClicked) {
        id = note.getNoteId();
        userId = note.getUser().getUserId();
        isbn = note.getBook().getIsbn();
        type = note.getType().toFeedType();
        userProfileImgName = note.getUser().getImgPath();
        userName = note.getUser().getNickName();
        bookCoverImgName = note.getBook().getCoverImgName();
        bookName = note.getBook().getTitle();
        content = note.getContent();
        createdAt = note.getCreatedAt();
        this.heartCount = heartCount;
        this.isHeartClicked = isHeartClicked;
    }
}
