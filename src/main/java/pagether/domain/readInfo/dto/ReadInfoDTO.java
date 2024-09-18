package pagether.domain.readInfo.dto;

import lombok.*;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.readInfo.domain.ReadStatus;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReadInfoDTO {
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    private ReadStatus status;
    private Boolean hasReview;
    private Float rating;
    private String review;

    @Builder
    public ReadInfoDTO(ReadInfo readInfo, Float rating, String review) {
        startDate = readInfo.getStartDate();
        completionDate = readInfo.getCompletionDate();
        hasReview = readInfo.getHasReview();
        this.rating = rating;
        this.review = review;
    }

    @Builder
    public ReadInfoDTO(ReadInfo readInfo) {
        startDate = readInfo.getStartDate();
        completionDate = readInfo.getCompletionDate();
    }
}
