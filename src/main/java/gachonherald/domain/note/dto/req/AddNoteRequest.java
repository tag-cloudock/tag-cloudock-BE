package gachonherald.domain.note.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import gachonherald.domain.note.domain.NoteType;

@Getter
@Jacksonized
@Builder
public class AddNoteRequest {
    private String isbn;
    private NoteType type;
    private String content;
    private Float rating;
    private String topic;
    private String sentence;
    private Boolean isPrivate;
    private Long discussionId;
    private Boolean hasSpoilerRisk;
}