package pagether.domain.note.dto.res;

import lombok.*;
import pagether.domain.note.domain.Note;
import pagether.domain.note.dto.NoteDTO;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class NotesResponse {

    private List<NoteDTO> notes;
    private Long nextCursor;

    @Builder
    public NotesResponse(List<NoteDTO> notes, Long nextCursor) {
        this.notes = notes;
        this.nextCursor = nextCursor;
    }
}
