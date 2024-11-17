package tagCloudock.domain.user.dto.res;

import tagCloudock.domain.user.dto.ReporterDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class ReportersResponse {
    private List<ReporterDTO> reporters;
    public ReportersResponse(List<ReporterDTO> reporters) {
        this.reporters = reporters;
    }
}