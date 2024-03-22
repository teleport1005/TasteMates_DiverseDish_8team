package TasteMates.DiverseDish.comment_review.review;

import TasteMates.DiverseDish.comment_review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            Integer score,
            String content,
            MultipartFile image,
            Model model
    ) {
        String username = authentication.getName();

        ReviewDto review = reviewService.createReview(recipeId, username, score, content, image);

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
