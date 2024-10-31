package gachonherald.domain.article.repository;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {


}