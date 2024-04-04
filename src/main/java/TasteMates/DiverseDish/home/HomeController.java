package TasteMates.DiverseDish.home;

import TasteMates.DiverseDish.recipe.RecipeService;
import TasteMates.DiverseDish.recipe.dto.RecipeDto;
import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import TasteMates.DiverseDish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final RecipeService recipeService;
    private final UserService userService;

    @GetMapping
    public String home(
            @PageableDefault(size = 20, sort = "id")
            Pageable pageable,
            @AuthenticationPrincipal() CustomUserDetails customUserDetails,
            Model model
    ) {
        // TODO Pageable
        // TODO 추천 레시피 조회
        Page<RecipeDto> recipes = recipeService.readAllPage(pageable);
        // TODO 쉐프 소개 조회
        // TODO 관리자 추천 레시피 조회

        log.info("customUserDetails.getUsername = {}", customUserDetails);

        boolean loginUser = isLogin(customUserDetails);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("recipes", recipes);

        // 세션 현재 사용자 아이디 가져오기
        SecurityContextHolder.getContext().getAuthentication().getName();

        return "/home";
    }

    private boolean isLogin(CustomUserDetails customUserDetails) {
        return customUserDetails != null;
    }

    @GetMapping("/search")
    public String search(String search) {

        //TODO 검색어 조회

        return "/search";
    }
}
