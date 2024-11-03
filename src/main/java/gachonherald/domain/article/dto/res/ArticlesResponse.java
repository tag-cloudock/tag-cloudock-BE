package gachonherald.domain.article.dto.res;

import gachonherald.domain.article.dto.ArticleDTO;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class ArticlesResponse {
    private List<ArticleDTO> articles;
    private int pageCount;
    @Builder
    public ArticlesResponse(List<ArticleDTO> articles, int pageCount) {
        this.articles = articles;
        this.pageCount = pageCount;
    }
    @Builder
    public ArticlesResponse(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}
