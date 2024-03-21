package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    // 레시피 생성
    @PostMapping
    public RecipeDto create(
            @RequestBody
            RecipeDto dto
    ) {
        return recipeService.create(dto);
    }

    // 레시피 읽기
    @GetMapping("/{id}")
    public RecipeDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return recipeService.readOne(id);
    }

    // 레시피 업데이트
    @PutMapping("/{id}")
    public RecipeDto updateRecipe(
            @PathVariable("id")
            Long id,
            @RequestBody
            RecipeDto dto
    ) {
        return recipeService.updateRecipe(id, dto);
    }

    // 레시피 삭제
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id")
            Long id
    ) {
        recipeService.delete(id);
    }

}
