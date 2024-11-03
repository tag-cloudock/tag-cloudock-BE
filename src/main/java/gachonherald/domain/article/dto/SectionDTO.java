package gachonherald.domain.article.dto;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import gachonherald.domain.section.domain.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SectionDTO {
    private Long sectionId;
    private String sectionName;
    private List<ArticleDTO> imageArticles;
    private List<ArticleDTO> articles;

    public SectionDTO(Section section, List<ArticleDTO> imageArticles, List<ArticleDTO> articles) {
        sectionId = section.getSectionId();
        sectionName = section.getName();
        this.imageArticles = imageArticles;
        this.articles = articles;
    }
}
