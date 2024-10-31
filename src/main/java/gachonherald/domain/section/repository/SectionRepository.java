package gachonherald.domain.section.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.section.domain.Section;

@Repository
@Transactional
public interface SectionRepository extends JpaRepository<Section, Long> {
}