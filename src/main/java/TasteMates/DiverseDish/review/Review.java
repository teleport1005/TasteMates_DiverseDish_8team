package TasteMates.DiverseDish.review;

import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String content;
    private int score;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Review createReview(Recipe recipe, User user, int score, String content, String image) {
        return Review.builder()
                .recipe(recipe)
                .user(user)
                .score(score)
                .content(content)
                .image(image)
                .build();
    }
}
