package pagether.domain.announcement.dto.res;

import pagether.domain.announcement.domain.Announcement;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnnoResponse {
    private Integer annoId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public AnnoResponse(Announcement anno) {
        annoId = anno.getAnnoId();
        title = anno.getTitle();
        content = anno.getContent();
        createdAt = anno.getCreatedAt();
    }
}
