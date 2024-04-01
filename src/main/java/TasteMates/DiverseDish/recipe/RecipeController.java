package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import TasteMates.DiverseDish.recipe.dto.ReceiveRecipeDto;
import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import TasteMates.DiverseDish.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final CookOrderService cookOrderService;

    // 레시피 생성
    @PostMapping
    public RecipeDto create(
            @RequestBody
            ReceiveRecipeDto receiveRecipeDto
    ) {
        RecipeDto recipeDto = recipeService.create(receiveRecipeDto.getRecipeDto());
        cookOrderService.createCookOrderList(recipeService.getRecipe(recipeDto.getId()), receiveRecipeDto.getCookOrderDtoList());
        return recipeDto;
    }

    // 레시피 읽기
    @GetMapping("/{id}")
    public RecipeDto readOne(
            @PathVariable("id")
            Long id
    ) {
        List<CookOrderDto> cookOrderDtoList = cookOrderService.readAllByRecipeId(id);
        // TODO: CookOrder List에서 하나씩 추출해서 넣어주기
        return recipeService.readOne(id);
    }

    // 요리 순서 전체 읽기
    @GetMapping("/{id}/cook_order")
    public List<CookOrderDto> readAllCookOrder(
            @PathVariable("id")
            Long id
    ) {
        return cookOrderService.readAllByRecipeId(id);
    }

    // 레시피 업데이트
    // 요리 순서는 전체 삭제 후 재생성
    @PutMapping("/{id}")
    public RecipeDto updateRecipe(
            @PathVariable("id")
            Long id,
            @RequestBody
            ReceiveRecipeDto receiveRecipeDto
    ) {
        // 기존꺼 삭제 후 재생성
        cookOrderService.deleteAllByRecipeId(id);
        cookOrderService.createCookOrderList(recipeService.getRecipe(id), receiveRecipeDto.getCookOrderDtoList());
        return recipeService.updateRecipe(id, receiveRecipeDto.getRecipeDto());
    }

    // 레시피 삭제
    // 연결된 요리순서도 모두 삭제
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id")
            Long id
    ) {
        cookOrderService.deleteAllByRecipeId(id);
        recipeService.delete(id);
    }
}
