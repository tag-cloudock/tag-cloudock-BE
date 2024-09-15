package pagether.domain.note.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import pagether.domain.note.domain.NoteType;

@Getter
@Jacksonized
@Builder
public class AddNoteRequest {
    private Long bookId;
    private NoteType type;
    private String content;
    private Boolean isPrivate;
}