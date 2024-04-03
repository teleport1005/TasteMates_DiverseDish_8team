package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.comment.CommentService;
import TasteMates.DiverseDish.comment.ResponseCommentDto;
import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import TasteMates.DiverseDish.recipe.dto.ReceiveRecipeDto;
import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import TasteMates.DiverseDish.review.ResponseReviewDto;
import TasteMates.DiverseDish.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final CookOrderService cookOrderService;
    private final CommentService commentService;
    private final ReviewService reviewService;

    // 레시피 생성
    @PostMapping
    @ResponseBody
    public ReceiveRecipeDto create(
            @RequestBody
            ReceiveRecipeDto receiveRecipeDto
    ) {
        RecipeDto recipeDto = recipeService.create(receiveRecipeDto.getRecipeDto());
        List<CookOrderDto> cookOrderDtoList = cookOrderService.createCookOrderList(recipeService.getRecipe(recipeDto.getId()), receiveRecipeDto.getCookOrderDtoList());

        ReceiveRecipeDto returnDto = new ReceiveRecipeDto();
        returnDto.setRecipeDto(recipeDto);
        returnDto.setCookOrderDtoList(cookOrderDtoList);
        return returnDto;
    }

    // 레시피 읽기
    @GetMapping("/{id}")
    public String readOne(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        RecipeDto recipeDto = recipeService.readOne(id);
        List<CookOrderDto> cookOrderDtoList = cookOrderService.readAllCookOrders(id);
        List<ResponseCommentDto> commentDtoList = commentService.readAllComments(id);
        List<ResponseReviewDto> reviewDtoList = reviewService.readAllReviews(id);

        model.addAttribute("recipe", recipeDto);
        model.addAttribute("cookOrderList", cookOrderDtoList);
        model.addAttribute("commentList", commentDtoList);
        model.addAttribute("reviewList", reviewDtoList);


        return "read";
    }

//    // 요리 순서 전체 읽기
//    @GetMapping("/{id}/cook_order")
//    public List<CookOrderDto> readAllCookOrder(
//            @PathVariable("id")
//            Long id
//    ) {
//        return cookOrderService.readAllByRecipeId(id);
//    }

    // 레시피 업데이트
    @PutMapping("/{id}")
    @ResponseBody
    public ReceiveRecipeDto updateRecipe(
            @PathVariable("id")
            Long id,
            @RequestBody
            ReceiveRecipeDto receiveRecipeDto
    ) {
        RecipeDto recipeDto = recipeService.updateRecipe(id, receiveRecipeDto.getRecipeDto());
        List<CookOrderDto> cookOrderDtoList = cookOrderService.updateCookOrder(recipeService.getRecipe(id), receiveRecipeDto.getCookOrderDtoList());

        ReceiveRecipeDto returnDto = new ReceiveRecipeDto();
        returnDto.setRecipeDto(recipeDto);
        returnDto.setCookOrderDtoList(cookOrderDtoList);
        return returnDto;
    }

    // 레시피 삭제
    // 연결된 요리순서도 모두 삭제
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(
            @PathVariable("id")
            Long id
    ) {
        cookOrderService.deleteAllByRecipeId(id);
        recipeService.delete(id);
    }
}
