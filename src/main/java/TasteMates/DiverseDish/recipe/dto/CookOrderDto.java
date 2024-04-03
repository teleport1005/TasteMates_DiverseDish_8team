package TasteMates.DiverseDish.recipe.dto;

import TasteMates.DiverseDish.recipe.entity.CookOrder;
import TasteMates.DiverseDish.recipe.entity.Recipe;
import lombok.*;
import org.hibernate.annotations.Fetch;

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
    private String image;

    public static  CookOrderDto fromEntity(CookOrder entity) {
        return CookOrderDto.builder()
                .id(entity.getId())
                .recipe(entity.getRecipe())
                .step(entity.getStep())
                .cooking_tip(entity.getCooking_tip())
                .image(entity.getImage())
                .build();
    }
}