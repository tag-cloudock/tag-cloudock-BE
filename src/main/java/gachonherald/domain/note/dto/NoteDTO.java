package gachonherald.domain.note.dto;

import lombok.*;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.note.domain.NoteType;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NoteDTO {
    private Long noteId;
    private String userName;
    private String userProfileImgName;
    private String imgName;
    private String content;
    private Long heartCount;
    private Float rating;
    private String topic;
    private String sentence;
    private Boolean isPrivate;
    private Boolean hasSpoilerRisk;
    private Boolean isHeartClicked;
    private NoteType type;
    private String bookName;
    private String bookCoverImgName;
    private String isbn;
    private LocalDateTime createdAt;

    public NoteDTO(Note note, Boolean isHeartClicked) {
        noteId = note.getNoteId();
        isbn = note.getBook().getIsbn();
        bookName = note.getBook().getTitle();
        bookCoverImgName = note.getBook().getCoverImgName();
        imgName = note.getImgName();
        userName = note.getUser().getNickName();
        userProfileImgName = note.getUser().getImgPath();
        type = note.getType();
        content = note.getContent();
        rating = note.getRating();
        topic = note.getTopic();
        sentence = note.getSentence();
        hasSpoilerRisk = note.getHasSpoilerRisk();
        isPrivate = note.getIsPrivate();
        this.isHeartClicked = isHeartClicked;
        heartCount = note.getHeartCount();
        createdAt = LocalDateTime.now();
    }
}
