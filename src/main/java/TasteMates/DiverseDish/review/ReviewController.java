package TasteMates.DiverseDish.review;


import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("recipe/{recipeId}/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public String createReview(
            @PathVariable("recipeId") Long recipeId,
            int score,
            @RequestParam("content")
            @Validated // 수정
            String content,
            BindingResult result,
            @RequestParam("image")
            MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model
    ) {

        /**
         * content가 null인 경우 Error 발생
         */
        if (result.hasErrors()) {
            return "redirect:/recipe/%d".formatted(recipeId);
        }

        if (customUserDetails == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String username = customUserDetails.getUsername();
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
