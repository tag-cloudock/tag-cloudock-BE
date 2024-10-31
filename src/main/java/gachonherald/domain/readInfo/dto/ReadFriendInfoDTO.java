package gachonherald.domain.readInfo.dto;

import lombok.*;
import gachonherald.domain.readInfo.domain.ReadInfo;
import gachonherald.domain.readInfo.domain.ReadStatus;


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
