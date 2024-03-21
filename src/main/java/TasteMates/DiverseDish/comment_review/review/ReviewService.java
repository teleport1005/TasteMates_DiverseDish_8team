package TasteMates.DiverseDish.comment_review.review;

import TasteMates.DiverseDish.comment_review.dto.ReviewDto;
import TasteMates.DiverseDish.comment_review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    public ReviewDto createReview(Long recipeId, String username, Integer score, String content, MultipartFile image) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        //TODO 이미지 저장 경로 로직 구현 필요
        String imagePath = "";

        Review review = Review.createReview(recipe, user, score, content, imagePath);

        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    public void deleteReview(Long recipeId, Long reviewId, String username) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 리뷰의 레시피가 맞는지
        if (!review.getRecipe().equals(recipe)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 리뷰을 작성한 유저가 맞는지
        if (!review.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        reviewRepository.deleteById(reviewId);
    }
}
