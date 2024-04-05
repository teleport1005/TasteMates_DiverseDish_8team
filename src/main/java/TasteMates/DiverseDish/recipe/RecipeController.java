package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.comment.CommentService;
import TasteMates.DiverseDish.comment.ResponseCommentDto;
import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import TasteMates.DiverseDish.recipe.dto.ReceiveRecipeDto;
import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import TasteMates.DiverseDish.review.ResponseReviewDto;
import TasteMates.DiverseDish.review.ReviewService;
import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final CookOrderService cookOrderService;
    private final CommentService commentService;
    private final ReviewService reviewService;

    // 레시피 생성
    @PostMapping
    public String create(@ModelAttribute RecipeDto recipeDto) throws IOException {
        RecipeDto resultDto = recipeService.create(recipeDto);
        resultDto.setCookOrderDtoList(recipeDto.getCookOrderDtoList());
        cookOrderService.createCookOrderList(resultDto);
        return "redirect:/recipe/"+resultDto.getId();
    }

    @GetMapping("/create-view")
    public String creatView() {
        return "register";
    }

    // 레시피 읽기
    @GetMapping("/{id}")
    public String readOne(
            @PathVariable("id")
            Long id,
            Model model,
            Principal principal
    ) {
        log.info("Current Principal Object = {}", principal);
//        principal.getName(); // username

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
    ) throws IOException {
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
        //TODO: 사용자 확인 후 삭제 필요
        cookOrderService.deleteAllCookOrders(id);
        recipeService.delete(id);
        commentService.deleteAllComments(id);
        reviewService.deleteAllReviews(id);
    }
}
