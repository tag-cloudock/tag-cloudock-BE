package pagether.domain.readInfo.dto.res;

import lombok.*;
import pagether.domain.readInfo.domain.ReadInfo;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadInfoResponse {
    private Long readId;
    private LocalDateTime createdAt;

    @Builder
    public ReadInfoResponse(ReadInfo readInfo) {
        readId = readInfo.getReadId();
        createdAt = readInfo.getCreatedAt();
    }
}
