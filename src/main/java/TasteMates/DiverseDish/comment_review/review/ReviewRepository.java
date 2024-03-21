package TasteMates.DiverseDish.comment_review.review;

import TasteMates.DiverseDish.comment_review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
