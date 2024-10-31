package gachonherald.domain.readInfo.dto.res;

import lombok.*;
import gachonherald.domain.readInfo.domain.ReadInfo;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadInfoResponse {
    private Long readInfoId;
    private LocalDateTime createdAt;

    @Builder
    public ReadInfoResponse(ReadInfo readInfo) {
        readInfoId = readInfo.getReadInfoId();
        createdAt = readInfo.getCreatedAt();
    }
}
