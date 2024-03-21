package TasteMates.DiverseDish.recipe.repo;

import TasteMates.DiverseDish.recipe.entity.CookOrder;
import TasteMates.DiverseDish.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CookOrderRepo extends JpaRepository<CookOrder, Long> {
    Optional<CookOrder> findById(Long Id);
    List<CookOrder> findByRecipe_idOrderByStepAsc(Long RecipeId);
}
