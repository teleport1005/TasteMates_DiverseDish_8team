package TasteMates.DiverseDish.home;

import TasteMates.DiverseDish.recipe.RecipeService;
import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecipeService recipeService;

    @GetMapping("/home")
    public String home(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model
    ) {

        //List<RecipeDto> recipes = recipeService.readAll();

        // TODO Pageable
        Page<RecipeDto> recipes = recipeService.readAll(pageable);

        // TODO 쉐프 소개 조회
        // TODO 관리자 추천 레시피 조회

        log.info("customUserDetails.getUsername = {}", customUserDetails);

        boolean loginUser = isLogin(customUserDetails);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("recipes", recipes);

        return "/home";
    }

    private boolean isLogin(CustomUserDetails customUserDetails) {
        return customUserDetails != null;
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("search")
            String search,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            Model model) {


        //List<RecipeDto> searchedRecipes = recipeService.searchRecipe(search);

        // TODO Pageable
        Page<RecipeDto> searchedRecipes = recipeService.searchRecipe(search, pageable);


        model.addAttribute("searchedRecipes", searchedRecipes);

        return "/search-page";
    }
}
