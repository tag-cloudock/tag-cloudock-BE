package pagether.domain.review.repository;


import pagether.domain.review.domain.RateType;
import pagether.domain.review.domain.Review;
import pagether.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findTop3ByRecipientOrderByCreatedAtDesc(User recipient);

    Integer countByRecipientAndRate(User recipient, RateType rate);
}
