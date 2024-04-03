package TasteMates.DiverseDish.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecipe_idOrderByIdAsc(Long RecipeId);
    void deleteByRecipe_id(Long RecipeId);
}
