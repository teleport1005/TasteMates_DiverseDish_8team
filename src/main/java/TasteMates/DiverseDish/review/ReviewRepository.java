package TasteMates.DiverseDish.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRecipe_idOrderByIdAsc(Long RecipeId);
    void deleteByRecipe_id(Long RecipeId);
}
