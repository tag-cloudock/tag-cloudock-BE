package gachonherald.domain.comment.repository;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.comment.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticleOrderByCreatedAtDesc(Article article, Pageable pageable);
    int countAllByArticle(Article article);
    List<Comment> findAllByOrderByCreatedAtDesc(Pageable pageable);

    int countAllBy();
}