package TasteMates.DiverseDish.user.oauth;

import TasteMates.DiverseDish.user.dto.UserDto;
import TasteMates.DiverseDish.user.entity.CustomUserDetails;
import TasteMates.DiverseDish.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  //  private final JwtTokenUtils tokenUtils; 나중에 jwt랑 합쳐야해
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oAuth2User
                =(OAuth2User)  authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");
        String username = String.format("{%s}%s", provider, email);
        String nickname = oAuth2User.getAttribute("nickname");
        String profileImage = oAuth2User.getAttribute("profile_image_url");
        String providerId = oAuth2User.getAttribute("id").toString();

        if (!userService.userExists(username)){
            userService.createUser(UserDto.builder() //RecipeUserDetails로 안만들어도 되는지 확인 필요
                    .username(username)
                    .password(providerId)
                    .nickname(nickname)
                    .email(email)
                    .profileImage(profileImage)
                    .build());
        }
        UserDetails details = userService.loadUserByUsername(username);
        //추후에 토큰 넣기
//        String jwt = tokenUtils.generateToken(details);
//        String targetUrl = String.format("http://localhost:8080/token/validate?token=%s", jwt);
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}
