package pagether.domain.note.dto.res;

import lombok.*;
import pagether.domain.news.domain.News;
import pagether.domain.note.domain.Note;

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
