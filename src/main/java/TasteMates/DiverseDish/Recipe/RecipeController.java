package TasteMates.DiverseDish.Recipe;

import TasteMates.DiverseDish.Recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService service;

    @PostMapping()
    public RecipeDto create(
            @RequestBody
            RecipeDto dto
    ) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public RecipeDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return service.readOne(id);
    }

}
