package pagether.domain.report.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.note.domain.Note;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.dto.res.NoteResponse;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.report.domain.Report;
import pagether.domain.report.dto.req.AddReportRequest;
import pagether.domain.report.dto.res.ReportResponse;
import pagether.domain.report.exception.ReportNotFountException;
import pagether.domain.report.repository.ReportRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

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

    public void delete(Long noteId) {
        if (!reportRepository.existsById(noteId)) {
            throw new ReportNotFountException();
        }
        reportRepository.deleteById(noteId);
    }
}