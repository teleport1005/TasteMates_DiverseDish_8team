package TasteMates.DiverseDish.Recipe.dto;

import TasteMates.DiverseDish.Recipe.entity.CookOrder;
import TasteMates.DiverseDish.Recipe.entity.Recipe;
import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CookOrderDto {
    private Long id;
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
