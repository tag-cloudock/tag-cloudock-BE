package gachonherald.domain.note.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.note.domain.NoteType;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentDTO {
    private Long noteId;
    private String userName;
    private String userProfileImgName;
    private String content;
    private Long heartCount;
    private Boolean isHeartClicked;
    private NoteType type;
    private LocalDateTime createdAt;

    public CommentDTO(Note note, Boolean isHeartClicked) {
        noteId = note.getNoteId();
        userName = note.getUser().getNickName();
        userProfileImgName = note.getUser().getImgPath();
        content = note.getContent();
        this.isHeartClicked = isHeartClicked;
        heartCount = note.getHeartCount();
        createdAt = LocalDateTime.now();
    }
}
