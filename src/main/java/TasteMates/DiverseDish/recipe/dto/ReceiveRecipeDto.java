package TasteMates.DiverseDish.recipe.dto;

import TasteMates.DiverseDish.user.dto.UserDto;
import lombok.*;

import java.util.List;

// 사용자에게서 레시피를 하나의 DTO로 받기 위함
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveRecipeDto {
    private RecipeDto recipeDto;
    private List<CookOrderDto> cookOrderDtoList;
}
