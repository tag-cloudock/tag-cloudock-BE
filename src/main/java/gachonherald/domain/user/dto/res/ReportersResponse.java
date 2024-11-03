package gachonherald.domain.user.dto.res;

import gachonherald.domain.user.dto.ReporterDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class ReportersResponse {
    private List<ReporterDTO> repoters;
    public ReportersResponse(List<ReporterDTO> repoters) {
        this.repoters = repoters;
    }
}