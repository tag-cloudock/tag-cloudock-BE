package pagether.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.note.domain.Note;
import pagether.domain.report.domain.Report;

import java.util.Optional;

@Repository
@Transactional
public interface ReportRepository extends JpaRepository<Report, Long> {
}