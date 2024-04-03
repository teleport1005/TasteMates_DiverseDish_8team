package TasteMates.DiverseDish.recipe;

<<<<<<< HEAD
import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
=======
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
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
import org.springframework.web.bind.annotation.*;

import java.util.List;

<<<<<<< HEAD
@RestController
=======
@Controller
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final CookOrderService cookOrderService;
<<<<<<< HEAD

    // 레시피 생성
    @PostMapping
    public RecipeDto create(
            @RequestBody
            RecipeDto dto,
            @RequestBody
            List<CookOrderDto> cookOrderDtoList
    ) {
        RecipeDto recipeDto = recipeService.create(dto);
        cookOrderService.createCookOrderList(recipeService.getRecipe(recipeDto.getId()), cookOrderDtoList);
        return recipeDto;
=======
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
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
    }

    // 레시피 읽기
    @GetMapping("/{id}")
<<<<<<< HEAD
    public RecipeDto readOne(
            @PathVariable("id")
            Long id
    ) {
        // TODO: CookOrder List에서 하나씩 추출해서 넣어주기
        List<CookOrderDto> cookOrderDtoList = cookOrderService.readAllByRecipeId(id);
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
            RecipeDto dto,
            @RequestBody
            List<CookOrderDto> cookOrderDtoList
    ) {
        // 기존꺼 삭제 후 재생성
        cookOrderService.deleteAllByRecipeId(id);
        cookOrderService.createCookOrderList(recipeService.getRecipe(id), cookOrderDtoList);
        return recipeService.updateRecipe(id, dto);
=======
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
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
    }

    // 레시피 삭제
    // 연결된 요리순서도 모두 삭제
    @DeleteMapping("/{id}")
<<<<<<< HEAD
=======
    @ResponseBody
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
    public void delete(
            @PathVariable("id")
            Long id
    ) {
<<<<<<< HEAD
        cookOrderService.deleteAllByRecipeId(id);
        recipeService.delete(id);
=======
        //TODO: 사용자 확인 후 삭제 필요
        cookOrderService.deleteAllCookOrders(id);
        recipeService.delete(id);
        commentService.deleteAllComments(id);
        reviewService.deleteAllReviews(id);
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
    }
}
