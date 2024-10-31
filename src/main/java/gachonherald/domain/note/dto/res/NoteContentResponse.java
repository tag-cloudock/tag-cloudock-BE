package gachonherald.domain.note.dto.res;

import lombok.*;
import gachonherald.domain.note.domain.Note;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteContentResponse {
    private Long noteId;
    private Float rating;
    private String topic;
    private String sentence;
    private String content;
    private Boolean isPrivate;
    private Boolean isHeartClicked;
    private Boolean hasSpoilerRisk;
    private String imgName;
    private LocalDateTime createdAt;

    @Builder
    public NoteContentResponse(Note note, Boolean isHeartClicked) {
        noteId = note.getNoteId();
        imgName = note.getImgName();
        rating = note.getRating();
        topic = note.getTopic();
        sentence = note.getSentence();
        content = note.getContent();
        isPrivate = note.getIsPrivate();
        hasSpoilerRisk = note.getHasSpoilerRisk();
        createdAt = note.getCreatedAt();
        this.isHeartClicked = isHeartClicked;
    }
}
