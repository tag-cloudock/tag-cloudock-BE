package gachonherald.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.category.domain.Category;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
}