package TasteMates.DiverseDish.recipe.repo;

import TasteMates.DiverseDish.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    Optional<Long> findByTitle(String title);
    Optional<Recipe> findById(Long Id);
}
