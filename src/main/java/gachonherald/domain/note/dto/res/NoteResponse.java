package gachonherald.domain.note.dto.res;

import lombok.*;
import gachonherald.domain.note.domain.Note;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteResponse {
    private Long noteId;
    private LocalDateTime createdAt;

    @Builder
    public NoteResponse(Note note) {
        noteId = note.getNoteId();
        createdAt = note.getCreatedAt();
    }
}
