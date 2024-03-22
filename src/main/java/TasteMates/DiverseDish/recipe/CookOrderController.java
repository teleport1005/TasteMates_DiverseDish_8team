package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cook_order")
@RequiredArgsConstructor
public class CookOrderController {
    private final CookOrderService cookOrderService;
    private final RecipeService recipeService;

    // 레시피 아이디에 맞게 저장
    @PostMapping("/{id}")
    public CookOrderDto create(
            @PathVariable("id")
            Long recipeId,
            @RequestBody
            CookOrderDto dto
    ) {
        dto.setRecipe(recipeService.getRecipe(recipeId));
        return cookOrderService.create(dto);
    }

    // 레시피에 연결된 요리 순서 모두 불러오기
    @GetMapping("/{id}")
    public List<CookOrderDto> readAll(
            @PathVariable("id")
            Long recipeId
    ) {
        return cookOrderService.readAllByRecipeId(recipeId);
    }

    // 업데이트
//    @PutMapping("/{id}")
//    public CookOrderDto update(
//            @PathVariable("id")
//            Long recipeId,
//            @RequestBody
//            CookOrderDto dto
////    ) {
////
////    }

    // 삭제
}
