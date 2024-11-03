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
public class SectionResponse {
    private String name;
    @Builder
    public SectionResponse(Section section) {
        name = section.getName();
    }
}
