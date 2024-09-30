package pagether.domain.note.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pagether.domain.note.domain.NoteType;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentDTO {
    private Long noteId;
    private String userName;
    private String userProfileImgName;
    private String content;
    private Long heartCount;
    private Boolean isHeartClicked;
    private NoteType type;
    private LocalDateTime createdAt;
}
