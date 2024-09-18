package pagether.domain.readInfo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pagether.domain.book.domain.Book;
import pagether.domain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class ReadInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readInfoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;

    @Column(nullable = false)
    private Long readCount;

    @Column(nullable = false)
    private ReadStatus readStatus;

    @Column(nullable = false)
    private Boolean hasReview;

    @Column(nullable = false)
    private Long currentPage;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;

    public void setCurrentPage(Long page) {
        this.currentPage = page;
    }
    public void start() {
        this.readStatus = ReadStatus.READING;
    }
    public void stop() {
        this.readStatus = ReadStatus.STOPPED;
    }
    public void done() {
        this.readStatus = ReadStatus.READ;
    }
}