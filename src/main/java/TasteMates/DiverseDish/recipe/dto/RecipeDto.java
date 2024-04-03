package TasteMates.DiverseDish.recipe.dto;

import TasteMates.DiverseDish.recipe.entity.Recipe;
<<<<<<< HEAD
import TasteMates.DiverseDish.user.entity.User;
=======
import TasteMates.DiverseDish.entity.UserEntity;
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private Long id;
<<<<<<< HEAD
    private User user;
=======
    private UserEntity user;
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b

    @Setter
    private String main_image;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private String video_link;
    @Setter
    private int view;
    @Setter
    private Recipe.Level level;
    @Setter
    private Recipe.Category category;
    @Setter
    private String  ingredient;
    @Setter
    private int approval; // 0이면 비공개, 1이면 공개

    public static RecipeDto fromEntity(Recipe entity) {
        return RecipeDto.builder()
                .id(entity.getId())
                .user(entity.getUser())
                .main_image(entity.getMain_image())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .video_link(entity.getVideo_link())
                .view(entity.getView())
                .level(entity.getLevel())
                .category(entity.getCategory())
                .ingredient(entity.getIngredient())
                .approval(entity.getApproval())
                .build();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 24ca87b642dd7d1d243d38e6797bf95ebb75180b
