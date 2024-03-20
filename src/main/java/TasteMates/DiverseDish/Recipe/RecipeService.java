package TasteMates.DiverseDish.Recipe;

import TasteMates.DiverseDish.Recipe.entity.Recipe;
import TasteMates.DiverseDish.Recipe.repo.RecipeRepo;
import TasteMates.DiverseDish.Recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepo repo;

    // 레시피 생성
    public RecipeDto create(
            RecipeDto dto
    ) {
        return RecipeDto.fromEntity(repo.save(Recipe.builder()
                .user(dto.getUser())
                .main_image(dto.getMain_image())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .video_link(dto.getVideo_link())
                .view(0) // 초기 조회수 0
                .level(dto.getLevel())
                .category(dto.getCategory())
                .ingredient(dto.getIngredient())
                .approval(0) // 승인을 해줘야 게시 가능, 초기엔 미승인 상태
                .build()));
    }

    // 레시피 조회
    public RecipeDto readOne(Long recipeId) {
        Recipe recipe = getRecipe(recipeId);
        return RecipeDto.fromEntity(recipe);
    }

    private Recipe getRecipe(Long id) {
        Optional<Recipe> optionalRecipe = repo.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalRecipe.get();
    }
}
