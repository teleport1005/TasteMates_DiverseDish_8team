package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.recipe.repo.RecipeRepository;
import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.entity.User;
import TasteMates.DiverseDish.user.repo.UserRepository;
import TasteMates.DiverseDish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepo;
    private final UserService userService;
    private final UserRepository userRepository;

    // 레시피 생성
    public RecipeDto create(RecipeDto dto) throws IOException {
        Optional<User> userEntity = userRepository.findByUsername(userService.myProfile().getUsername());
        Files.createDirectories(Path.of("media"));
        UUID uuid = UUID.randomUUID();
        Path path = Path.of("media/" + "_" + uuid + dto.getMain_image().getOriginalFilename()); // 해당 파일의 이름을 경로를 포함해서 지정
        dto.getMain_image().transferTo(path); // 위에서 지정한 경로로 해당 파일 저장

        return RecipeDto.fromEntity(recipeRepo.save(Recipe.builder()
                .user(userEntity.get()) // TODO: User Entity 추가 필요
                .title(dto.getTitle())
                .description(dto.getDescription())
                .video_link(dto.getVideo_link())
                .level(dto.getLevel())
                .category(dto.getCategory())
                .ingredient(dto.getIngredient())
                .main_image_url("/" + path)
                .build()));
    }

    // 레시피 조회
    public RecipeDto readOne(Long recipeId) {
        Recipe recipe = getRecipe(recipeId);
        return RecipeDto.fromEntity(recipe);
    }

    // 여러 레시피 조회
    public Page<RecipeDto> readAllPage(Pageable pageable) {
        Page<Recipe> recipePage = recipeRepo.findAll(pageable);
        return recipePage.map(RecipeDto::fromEntity);
    }

    // 레시피 업데이트
    public RecipeDto updateRecipe(Long recipeId, RecipeDto dto) {
        Recipe recipe = getRecipe(recipeId);

//        recipe.setMain_image(dto.getMain_image());
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
}