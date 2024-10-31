package gachonherald.domain.report.dto.res;

import lombok.*;
import gachonherald.domain.report.domain.Report;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportResponse {
    private Long reportId;
    private LocalDateTime createdAt;

    @Builder
    public ReportResponse(Report report) {
        reportId = report.getReportId();
        createdAt = report.getCreatedAt();
    }
}
