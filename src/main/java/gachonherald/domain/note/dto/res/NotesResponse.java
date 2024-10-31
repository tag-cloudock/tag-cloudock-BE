package gachonherald.domain.note.dto.res;

import lombok.*;
import gachonherald.domain.note.dto.NoteDTO;

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
