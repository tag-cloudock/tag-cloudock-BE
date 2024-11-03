package gachonherald.domain.article.dto.res;

import gachonherald.domain.article.dto.ArticleDTO;
import gachonherald.domain.article.dto.SectionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class HomeArticlesResponse {
    private List<ArticleDTO> editorsPicks;
    private List<SectionDTO> sections;
    @Builder
    public HomeArticlesResponse(List<ArticleDTO> editorsPicks, List<SectionDTO> sections) {
        this.editorsPicks = editorsPicks;
        this.sections = sections;
    }
}
