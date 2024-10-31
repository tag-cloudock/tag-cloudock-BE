package gachonherald.domain.readInfo.dto;

import lombok.*;
import gachonherald.domain.readInfo.domain.ReadInfo;
import gachonherald.domain.readInfo.domain.ReadStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadInfoDTO {
    private Long readInfoId;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    private ReadStatus status;
    private Boolean hasReview;
    private Long readingRate;
    private Float rating;
    private String review;

    @Builder
    public ReadInfoDTO(ReadInfo readInfo, Float rating, String review) {
        readInfoId = readInfo.getReadInfoId();
        startDate = readInfo.getStartDate();
        completionDate = readInfo.getCompletionDate();
        readingRate =(long)(readInfo.getCurrentPage() / (float)readInfo.getBook().getPageCount() * 100);
        hasReview = readInfo.getHasReview();
        status = readInfo.getReadStatus();
        this.rating = rating;
        this.review = review;
    }

    @Builder
    public ReadInfoDTO(ReadInfo readInfo, Float rating) {
        readInfoId = readInfo.getReadInfoId();
        startDate = readInfo.getStartDate();
        completionDate = readInfo.getCompletionDate();
        readingRate =(long)(readInfo.getCurrentPage() / (float)readInfo.getBook().getPageCount() * 100);
        hasReview = readInfo.getHasReview();
        status = readInfo.getReadStatus();
        this.rating = rating;
    }

    @Builder
    public ReadInfoDTO(ReadInfo readInfo) {
        readInfoId = readInfo.getReadInfoId();
        startDate = readInfo.getStartDate();
        status = readInfo.getReadStatus();
        completionDate = readInfo.getCompletionDate();
        readingRate =(long)(readInfo.getCurrentPage() / (float)readInfo.getBook().getPageCount() * 100);
    }
}
