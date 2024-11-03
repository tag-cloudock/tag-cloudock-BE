package gachonherald.domain.section.repository;

import gachonherald.domain.section.domain.SectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.section.domain.Section;

import java.util.List;

@Repository
@Transactional
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findAllByStatusOrderByOrderNumber(SectionStatus status);
}