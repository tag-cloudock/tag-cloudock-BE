package pagether.domain.readInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.readInfo.domain.ReadStatus;
import pagether.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReadInfoRepository extends JpaRepository<ReadInfo, Long> {
    Boolean existsByBookAndUserAndReadStatus(Book book, User user, ReadStatus readStatus);
    Boolean existsByBookAndUser(Book book, User user);
    Optional<ReadInfo> findByBookAndUserAndReadStatus(Book book, User user, ReadStatus readStatus);

    List<ReadInfo> findAllByBookAndUserOrderByStartDateDesc(Book book, User user);

    List<ReadInfo> findAllByUserAndReadStatus(User user, ReadStatus readStatus);

    Optional<ReadInfo> findByBookAndUserAndIsLatest(Book book, User user, Boolean isLatest);

    List<ReadInfo> findAllByUserAndIsLatest(User user, Boolean isLatest);


    List<ReadInfo> findAllByBookAndUserAndIsLatestAndReadStatusNotInOrderByCreatedAtDesc(Book book, User user, boolean isLatest, List<ReadStatus> excludedStatuses);



}