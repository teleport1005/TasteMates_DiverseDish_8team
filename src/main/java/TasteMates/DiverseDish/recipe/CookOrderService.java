package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.CookOrderDto;
import TasteMates.DiverseDish.recipe.entity.CookOrder;
import TasteMates.DiverseDish.recipe.entity.Recipe;
import TasteMates.DiverseDish.recipe.repo.CookOrderRepository;
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
    private final CookOrderRepository cookOrderRepo;

    public CookOrderDto createCookOrder(Recipe recipe, CookOrderDto dto) {
         return CookOrderDto.fromEntity(cookOrderRepo.save(
                CookOrder.builder()
                        .recipe(recipe)
                        .step(dto.getStep())
                        .cooking_tip(dto.getCooking_tip())
                        .image(dto.getImage())
                        .build()));
    }

    public List<CookOrderDto> createCookOrderList(
            Recipe recipe,
            List<CookOrderDto> cookOrderDtoList
    ) {
        List<CookOrderDto> list = new ArrayList<>();
        for (int i = 0; i < cookOrderDtoList.size(); i++) {
            list.add(createCookOrder(recipe, cookOrderDtoList.get(i)));
        }
        return list;
    }

    // 레시피에 연결된 모든 CookOrder를 Step순으로 정렬하여 DTO 반환
    public List<CookOrderDto> readAllCookOrders(Long recipeId) {
        List<CookOrderDto> list = new ArrayList<>();
        List<CookOrder> cookOrderList = cookOrderRepo.findByRecipe_idOrderByStepAsc(recipeId);
        for (int i = 0; i < cookOrderList.size(); i++) {
            list.add(CookOrderDto.fromEntity(cookOrderList.get(i)));
        }
        return list;
    }

    public List<CookOrderDto> updateCookOrder(Recipe recipe, List<CookOrderDto> cookOrderDtoList) {
        // 업데이트할 cookOrder 개수
        int nowSize = cookOrderDtoList.size();
        // 반환할 리스트
        List<CookOrderDto> returnCookOrderDtoList = new ArrayList<>();
        // 기존 레시피와 연결된 cookOrder 불러오기
        List<CookOrder> previousCookOrderList = cookOrderRepo.findByRecipe_idOrderByIdAsc(recipe.getId());

        // 기존이 많거나 같은 경우
        if (previousCookOrderList.size() >= nowSize) {
            for (int i = 0; i < previousCookOrderList.size(); i++) {
                if (i >= nowSize)
                    deleteCookOrder(previousCookOrderList.get(i).getId());
                else {
                    CookOrder cookOrder = previousCookOrderList.get(i);
                    CookOrderDto dto = cookOrderDtoList.get(i);
                    cookOrder.setStep(dto.getStep());
                    cookOrder.setCooking_tip(dto.getCooking_tip());
                    cookOrder.setImage(dto.getImage());

                    returnCookOrderDtoList.add(CookOrderDto.fromEntity(cookOrderRepo.save(cookOrder)));
                }
            }
        }
        // 추가해야 하는 경우
        else {
            for (int i = 0; i < nowSize; i++) {
                if (i >= previousCookOrderList.size())
                    returnCookOrderDtoList.add(createCookOrder(recipe, cookOrderDtoList.get(i)));
                else {
                    CookOrder cookOrder = previousCookOrderList.get(i);
                    CookOrderDto dto = cookOrderDtoList.get(i);
                    cookOrder.setStep(dto.getStep());
                    cookOrder.setCooking_tip(dto.getCooking_tip());
                    cookOrder.setImage(dto.getImage());

                    returnCookOrderDtoList.add(CookOrderDto.fromEntity(cookOrderRepo.save(cookOrder)));
                }
            }
        }
        return  returnCookOrderDtoList;
    }

    public void deleteCookOrder(Long id) {
        cookOrderRepo.deleteById(id);
    }

    public void deleteAllCookOrders(Long recipeId) {
        List<CookOrderDto> cookOrderDtoList = readAllCookOrders(recipeId);
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
