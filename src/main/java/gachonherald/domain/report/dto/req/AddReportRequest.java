package gachonherald.domain.report.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import gachonherald.domain.report.domain.ReportType;

@Getter
@Jacksonized
@Builder
public class AddReportRequest {
    private Long noteId;
    private ReportType type;
}