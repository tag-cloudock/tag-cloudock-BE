package gachonherald.domain.report.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.note.exception.NoteNotFountException;
import gachonherald.domain.note.repository.NoteRepository;
import gachonherald.domain.report.domain.Report;
import gachonherald.domain.report.dto.req.AddReportRequest;
import gachonherald.domain.report.dto.res.ReportResponse;
import gachonherald.domain.report.exception.ReportNotFountException;
import gachonherald.domain.report.repository.ReportRepository;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public ReportResponse save(AddReportRequest request, String userId) {
        Note note = noteRepository.findById(request.getNoteId()).orElseThrow(NoteNotFountException::new);
        User reporter = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Report report = Report.builder()
                .note(note)
                .reporter(reporter)
                .type(request.getType())
                .createdAt(LocalDateTime.now())
                .build();
        report = reportRepository.save(report);
        return new ReportResponse(report);
    }

    public void delete(Long reportId) {
        if (!reportRepository.existsById(reportId))
            throw new ReportNotFountException();
        reportRepository.deleteById(reportId);
    }
}