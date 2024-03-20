package TasteMates.DiverseDish.recipe;

import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService service;

    @PostMapping
    public RecipeDto create(
            @RequestBody
            RecipeDto dto
    ) {
        return service.create(dto);
//        return "create 완료";
    }

    @GetMapping("/{id}")
    public RecipeDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return service.readOne(id);
    }

}
