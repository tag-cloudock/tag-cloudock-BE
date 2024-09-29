package pagether.domain.note.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import pagether.domain.note.domain.NoteType;

@Getter
@Jacksonized
@Builder
public class UpdateNoteRequest {
    private String content;
    private Float rating;
    private String topic;
    private String sentence;
    private Boolean isPrivate;
    private Boolean hasSpoilerRisk;
}