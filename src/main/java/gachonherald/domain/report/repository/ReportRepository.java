package gachonherald.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.report.domain.Report;

@Repository
@Transactional
public interface ReportRepository extends JpaRepository<Report, Long> {
}