package pagether.domain.heart.dto.res;

import lombok.*;
import pagether.domain.follow.domain.Follow;
import pagether.domain.heart.domain.Heart;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HeartResponse {
    private Long heartId;
    private LocalDateTime createdAt;

    @Builder
    public HeartResponse(Heart heart) {
        heartId = heart.getHeartId();
        createdAt = heart.getCreatedAt();
    }
}
