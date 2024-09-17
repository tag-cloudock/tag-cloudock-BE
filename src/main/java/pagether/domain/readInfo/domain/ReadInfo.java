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
    private Long readId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;

    @Column(nullable = false)
    private Long readCount;

    @Column(nullable = false)
    private Boolean isCompleted;

    @Column(nullable = false)
    private Boolean hasReview;

    @Column(nullable = false)
    private Boolean toReadLater;

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
        this.toReadLater = false;
    }
}