package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import TasteMates.DiverseDish.recipe.entity.CookOrder;
import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.recipe.repo.CookOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CookOrderService {
    private final CookOrderRepo cookOrderRepo;

    public List<CookOrderDto> createCookOrderList(
            Recipe recipe,
            List<CookOrderDto> cookOrderDtoList
    ) {
        List<CookOrderDto> list = new ArrayList<>();
        for (int i = 0; i < cookOrderDtoList.size(); i++) {
            CookOrderDto dto = cookOrderDtoList.get(i);
            list.add(CookOrderDto.fromEntity(cookOrderRepo.save(
                    CookOrder.builder()
                            .recipe(recipe)
                            .step(dto.getStep())
                            .cooking_tip(dto.getCooking_tip())
                            .image(dto.getImage())
                            .build())));
        }
        return list;
    }

    public List<CookOrderDto> readAllByRecipeId(Long recipeId) {
        List<CookOrderDto> list = new ArrayList<>();
        List<CookOrder> cookOrderList = cookOrderRepo.findByRecipe_idOrderByStepAsc(recipeId);
        for (int i = 0; i < cookOrderList.size(); i++) {
            list.add(CookOrderDto.fromEntity(cookOrderList.get(i)));
        }
        return list;
    }

    public void deleteAllByRecipeId(Long recipeId) {
        List<CookOrderDto> cookOrderDtoList = readAllByRecipeId(recipeId);
        for (int i = 0; i < cookOrderDtoList.size(); i++) {
            cookOrderRepo.delete(getCookOrder(cookOrderDtoList.get(i).getId()));
        }
    }

    public CookOrder getCookOrder(Long id) {
        Optional<CookOrder> optionalCookOrder = cookOrderRepo.findById(id);
        if (optionalCookOrder.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optionalCookOrder.get();
    }
}
