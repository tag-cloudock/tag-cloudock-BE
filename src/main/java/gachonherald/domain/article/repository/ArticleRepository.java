package gachonherald.domain.article.repository;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import gachonherald.domain.section.domain.Section;
import gachonherald.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllBySectionAndStatusInOrderByArticleIdDesc(Section section, List<ArticleStatus> statuses, Pageable pageable);
    List<Article> findAllBySectionAndStatusOrderByArticleIdDesc(Section section, ArticleStatus statuses);
    int countAllBySectionAndStatusIn(Section section, List<ArticleStatus> statuses);
    List<Article> findAllByReporterAndStatusInOrderByArticleIdDesc(User reporter, List<ArticleStatus> statuses, Pageable pageable);
    int countAllByReporterAndStatusIn(User reporter, List<ArticleStatus> statuses);
    List<Article> findAllByReporterAndStatusNotInOrderByArticleIdDesc(User reporter, List<ArticleStatus> statuses);

    List<Article> findAllByIsEditorsPickOrderBySection_OrderNumber(Boolean isEditorsPick);
}