package pagether.domain.note.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import pagether.domain.note.domain.NoteType;

@Getter
@Jacksonized
@Builder
public class AddNoteRequest {
    private String isbn;
    private NoteType type;
    private String content;
    private Float rating;
    private String topic;
    private Boolean isPrivate;
    private Boolean hasSpoilerRisk;
}