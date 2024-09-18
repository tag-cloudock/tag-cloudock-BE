package pagether.domain.note.dto;

import lombok.*;
import pagether.domain.note.domain.Note;
import pagether.domain.note.domain.NoteType;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NoteDTO {
    private Long noteId;
    private String userName;
    private String userProfileImgName;
    private String content;
    private Long heartCount;
    private Float rating;
    private Boolean isPrivate;
    private Boolean hasSpoilerRisk;
    private Boolean isHeartClicked;
    private NoteType type;
    private String bookName;
    private String isbn;
    private LocalDateTime createdAt;
}
