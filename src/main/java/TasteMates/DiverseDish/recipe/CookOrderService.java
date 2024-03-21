package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import TasteMates.DiverseDish.recipe.entity.CookOrder;
import TasteMates.DiverseDish.recipe.repo.CookOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CookOrderService {
    private final CookOrderRepo cookOrderRepo;

    public CookOrderDto create(
            CookOrderDto dto
    ) {
        return CookOrderDto.fromEntity(cookOrderRepo.save(
                CookOrder.builder()
                        .recipe(dto.getRecipe())
                        .step(dto.getStep())
                        .cooking_tip(dto.getCooking_tip())
                        .image(dto.getImage())
                        .build()));
    }

    public List<CookOrderDto> readAllByRecipeId(Long recipeId) {
        List<CookOrderDto> list = new ArrayList<>();
        List<CookOrder> cookOrderList = cookOrderRepo.findByRecipe_idOrderByStepAsc(recipeId);
        for (int i = 0; i < cookOrderList.size(); i++) {
            list.add(CookOrderDto.fromEntity(cookOrderList.get(i)));
        }
        return list;
    }
}
