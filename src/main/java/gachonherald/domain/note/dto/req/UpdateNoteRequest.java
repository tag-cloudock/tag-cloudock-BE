package gachonherald.domain.note.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

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