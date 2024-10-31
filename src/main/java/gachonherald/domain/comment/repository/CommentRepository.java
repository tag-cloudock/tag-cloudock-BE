package gachonherald.domain.comment.repository;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {


}