package TasteMates.DiverseDish.comment_review.comment;

import TasteMates.DiverseDish.comment_review.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
