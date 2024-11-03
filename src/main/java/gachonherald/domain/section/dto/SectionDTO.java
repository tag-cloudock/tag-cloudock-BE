package gachonherald.domain.section.dto;

import gachonherald.domain.section.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SectionDTO {
    private Long sectionId;
    private String name;


    public SectionDTO(Section section) {
        sectionId = section.getSectionId();
        name = section.getName();
    }
}
