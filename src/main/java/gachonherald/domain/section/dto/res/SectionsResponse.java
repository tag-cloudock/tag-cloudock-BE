package gachonherald.domain.section.dto.res;

import gachonherald.domain.section.domain.Section;
import gachonherald.domain.section.dto.SectionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class SectionsResponse {
    private List<SectionDTO> activeSections;
    private List<SectionDTO> inactiveSections;
    @Builder
    public SectionsResponse(List<SectionDTO> activeSections, List<SectionDTO> inactiveSections) {
        this.activeSections = activeSections;
        this.inactiveSections = inactiveSections;
    }
}
