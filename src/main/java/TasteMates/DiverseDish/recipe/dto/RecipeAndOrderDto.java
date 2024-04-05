package TasteMates.DiverseDish.recipe.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeAndOrderDto {
    private RecipeDto recipe;
    private List<CookOrderDto> cookOrder;
}
