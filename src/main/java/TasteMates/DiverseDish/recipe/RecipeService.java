package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.recipe.repo.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepo;

    // 레시피 생성
    public RecipeDto create(
            RecipeDto dto
    ) {
        return RecipeDto.fromEntity(recipeRepo.save(Recipe.builder()
                .user(null) // TODO: User Entity 추가 필요
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

    // 레시피 업데이트
    public RecipeDto updateRecipe(Long recipeId, RecipeDto dto) {
        Recipe recipe = getRecipe(recipeId);

        recipe.setMain_image(dto.getMain_image());
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setVideo_link(dto.getVideo_link());
        recipe.setLevel(dto.getLevel());
        recipe.setCategory(dto.getCategory());
        recipe.setIngredient(dto.getIngredient());
        // TODO: 업데이트한 경우 재승인할지

        return RecipeDto.fromEntity(recipeRepo.save(recipe));
    }

    // 레시피 삭제
    public void delete(Long recipeId) {
        recipeRepo.delete(getRecipe(recipeId));
    }


    // ID로 레시피 조회하기
    public Recipe getRecipe(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepo.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalRecipe.get();
    }

    // 검색어로 조회
    public List<RecipeDto> searchRecipe(String search) {
        return recipeRepo.searchRecipe(search).stream()
                .map(RecipeDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<RecipeDto> readAll() {
        return recipeRepo.findAll().stream()
                .map(RecipeDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<RecipeDto> searchRecipe(String search, Pageable pageable) {
        return recipeRepo.searchRecipe(search, pageable).map(RecipeDto::fromEntity);
    }

    public Page<RecipeDto> readAll(Pageable pageable) {
        return recipeRepo.findAll(pageable).map(RecipeDto::fromEntity);
    }
}