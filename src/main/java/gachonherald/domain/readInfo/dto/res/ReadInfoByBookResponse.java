package gachonherald.domain.readInfo.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import gachonherald.domain.readInfo.domain.ReadInfo;
import gachonherald.domain.readInfo.domain.ReadStatus;
import gachonherald.domain.readInfo.dto.ReadInfoDTO;

import java.util.List;


//@NoArgsConstructor
@Getter
@Setter
public class ReadInfoByBookResponse {
    private Long readInfoId;
    private Long currentPage;
    private String bookName;
    private String userName;
    private String userProfileImgName;
    private Boolean hasReview;
    private ReadStatus currentStatus;
    private List<ReadInfoDTO> readInfos;

    @Builder
    public ReadInfoByBookResponse(ReadInfo lastReadInfo, Long readInfoId, Long currentPage, ReadStatus currentStatus, List<ReadInfoDTO> readInfos) {
        this.readInfoId = readInfoId;
        this.currentPage = currentPage;
        this.currentStatus = currentStatus;
        this.readInfos = readInfos;
        hasReview = lastReadInfo.getHasReview();
        bookName = lastReadInfo.getBook().getTitle();
        userName = lastReadInfo.getUser().getNickName();
        userProfileImgName = lastReadInfo.getUser().getImgPath();
    }

    @Builder
    public ReadInfoByBookResponse() {
        this.currentStatus =  null;
    }
}
