package pagether.domain.readInfo.dto;

import lombok.*;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.readInfo.domain.ReadStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadFriendInfoDTO {
    private String userId;
    private String userProfileImgName;
    private Boolean isReading;

    @Builder
    public ReadFriendInfoDTO(ReadInfo readInfo) {
        userId = readInfo.getUser().getUserId();
        userProfileImgName = readInfo.getUser().getImgPath();
        isReading = readInfo.getReadStatus().equals(ReadStatus.READING);
    }
}
