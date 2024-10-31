package gachonherald.domain.report.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Note note;

    @Column(nullable = false)
    private ReportType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User reporter;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}