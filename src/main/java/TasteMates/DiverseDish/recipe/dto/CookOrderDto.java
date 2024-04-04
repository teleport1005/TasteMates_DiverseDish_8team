
package TasteMates.DiverseDish.recipe.dto;

import TasteMates.DiverseDish.recipe.entity.CookOrder;
import TasteMates.DiverseDish.recipe.entity.Recipe;
import lombok.*;

import org.hibernate.annotations.Fetch;
import org.springframework.web.multipart.MultipartFile;


@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CookOrderDto {
    private Long id;
    @Setter
    private Recipe recipe;
    @Setter
    private int step;
    @Setter
    private String cooking_tip;
    @Setter
    private MultipartFile image;
    @Setter
    private String image_url;

    public static CookOrderDto fromEntity(CookOrder entity) {
        return CookOrderDto.builder()
                .id(entity.getId())
                .recipe(entity.getRecipe())
                .step(entity.getStep())
                .cooking_tip(entity.getCooking_tip())
                .image_url(entity.getImage_url())
                .build();
    }
}