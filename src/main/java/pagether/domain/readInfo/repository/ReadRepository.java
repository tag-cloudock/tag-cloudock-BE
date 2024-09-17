package pagether.domain.readInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.user.domain.User;

import java.util.Optional;

@Repository
@Transactional
public interface ReadRepository extends JpaRepository<ReadInfo, Long> {
    Boolean existsByBookAndUserAndToReadLater(Book book, User user, Boolean toReadLater);
    Optional<ReadInfo> findByBookAndUserAndToReadLater(Book book, User user, Boolean toReadLater);
}