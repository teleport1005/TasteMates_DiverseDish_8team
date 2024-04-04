package TasteMates.DiverseDish.recipe.dto;

import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.entity.UserEntity;
import TasteMates.DiverseDish.user.entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private Long id;
    private User user;
    @Setter
    private MultipartFile main_image;
    @Setter
    private String main_image_url;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private String video_link;
    @Setter
    private Recipe.Level level;
    @Setter
    private Recipe.Category category;
    @Setter
    private String ingredient;
    @Setter
    private List<CookOrderDto> cookOrderDtoList;

    public static RecipeDto fromEntity(Recipe entity) {
        return RecipeDto.builder()
                .id(entity.getId())
                .user(entity.getUser())
                .main_image_url(entity.getMain_image_url())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .video_link(entity.getVideo_link())
                .level(entity.getLevel())
                .category(entity.getCategory())
                .ingredient(entity.getIngredient())
                .build();
    }
}
