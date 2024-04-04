package TasteMates.DiverseDish.review;

import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.recipe.repo.RecipeRepository;
import TasteMates.DiverseDish.user.entity.User;
import TasteMates.DiverseDish.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private static final String FILE_DIR = "review_image/";

    private final RecipeRepository recipeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;


    public ResponseReviewDto createReview(Long recipeId, String username, int score, String content, MultipartFile image) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        String imagePath = storeFile(recipeId, username, image);

        Review review = Review.createReview(recipe, user, score, content, imagePath);

        return ResponseReviewDto.fromEntity(reviewRepository.save(review));
    }

    public List<ResponseReviewDto> readAllReviews(Long recipeId) {
        List<ResponseReviewDto> list = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findByRecipe_idOrderByIdAsc(recipeId);
        for (int i = 0; i < reviewList.size(); i++) {
            list.add(ResponseReviewDto.fromEntity(reviewList.get(i)));
        }
        return list;
    }

    public void deleteAllReviews(Long recipeId) {
        reviewRepository.deleteByRecipe_id(recipeId);
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

    // Full Path: review_image/{recipeId}_username 으로 구성
    // 예) review_image/1_TestUser
    private static String storeFile(Long recipeId, String username, MultipartFile image) {

        String imagePath = String.format("%s%d_%s", FILE_DIR, recipeId, username);

        try {
            image.transferTo(Path.of(imagePath));
        } catch (IOException e) {
            log.error("storeFile Error", e);
        }

        return imagePath;
    }
}
