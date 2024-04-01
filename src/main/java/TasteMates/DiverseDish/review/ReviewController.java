package TasteMates.DiverseDish.review;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("recipe/{recipeId}/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public String createReview(
            @PathVariable("recipeId") Long recipeId,
            Authentication authentication,
            int score,
            @RequestParam("content")
            @Validated // 수정
            String content,
            @RequestParam("image")
            MultipartFile image,
            Model model,
            BindingResult result
    ) {

        /**
         * content가 null인 경우 Error 발생
         */
        if (result.hasErrors()) {
            return "redirect:/recipe/%d".formatted(recipeId);
        }

        String username = authentication.getName();

        ResponseReviewDto review = reviewService.createReview(recipeId, username, score, content, image);

        model.addAttribute("review", review);
        return "redirect:/recipe/{%d}".formatted(recipeId);
    }

    @DeleteMapping("{reviewId}")
    public String deleteReview(
            @PathVariable("recipeId")
            Long recipeId,
            @PathVariable("reviewId")
            Long reviewId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        reviewService.deleteReview(recipeId, reviewId, username);
        return "redirect:/recipe/{%d}".formatted(recipeId);
    }
}
