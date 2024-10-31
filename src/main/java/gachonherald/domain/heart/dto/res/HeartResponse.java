package gachonherald.domain.heart.dto.res;

import lombok.*;
import gachonherald.domain.heart.domain.Heart;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HeartResponse {
    private Long heartId;
    private LocalDateTime createdAt;

    public HeartResponse(Heart heart) {
        heartId = heart.getHeartId();
        createdAt = heart.getCreatedAt();
    }
}
