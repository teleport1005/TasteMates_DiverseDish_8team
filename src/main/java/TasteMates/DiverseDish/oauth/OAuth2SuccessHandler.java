package TasteMates.DiverseDish.oauth;

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
// OAuth2UserServiceImpl이 성공적으로 OAuth2 과정을 마무리 했을 때,
// 넘겨받은 사용자 정보를 바탕으로 JWT를 생성,
// 클라이언트한테 JWT를 전달.
// 인증에 성공했을 때 특정 URL로 리다이렉트 하고 싶은 경우 활용 가능한 SuccessHandler
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // OAuth2UserServiceImpl의 반환값이 할당된다.
        OAuth2User oAuth2User
                = (OAuth2User) authentication.getPrincipal();

        // (OAuth2UserServiceImpl에서)넘겨받은 정보를 바탕으로 사용자 정보를 준비
        String email = oAuth2User.getAttribute("email");
        String provider = oAuth2User.getAttribute("provider");
        String username = String.format("{%s}%s", provider, email);
        String nickname = oAuth2User.getAttribute("nickname");
        String profileImage = oAuth2User.getAttribute("profile_image_url");
        String providerId = oAuth2User.getAttribute("id").toString();

        // 처음으로 이 소셜 로그인으로 로그인을 시도했다.
        if (!userService.userExists(username)) {
            // 새 계정을 만들어야 한다.
            userService.createUser(UserDto.builder()
                    .username(username)
                    .password(providerId)
                    .nickname(nickname)
                    .email(email)
                    .profileImage(profileImage)
                    .build());
        }
    }
}



