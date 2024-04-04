package TasteMates.DiverseDish.recipe.repo;

import TasteMates.DiverseDish.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Long> findByTitle(String title);
    Optional<Recipe> findById(Long Id);

    @Query("select r from Recipe r where r.title = :search OR r.description = :search OR r.ingredient = :search")
    List<Recipe> searchRecipe(@Param("search") String search);
}