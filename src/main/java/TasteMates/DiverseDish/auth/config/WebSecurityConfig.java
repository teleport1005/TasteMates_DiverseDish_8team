package TasteMates.DiverseDish.auth.config;

import TasteMates.DiverseDish.oauth.OAuth2SuccessHandler;
import TasteMates.DiverseDish.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.RequestPath;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                ""
//                        )
//                        .permitAll()
                                .requestMatchers(
                                        "/users/login",
                                        "/users/signup"
                                )
                                .anonymous()
                                .requestMatchers(
                                        "/users/info",
                                        "/users/update",
                                        "/users/{userId}/updateImg",
                                        "/recipe",
                                        "/recipe/{id}",
                                        "/recipe/{id}/cook_order",
                                        "recipe/{recipeId}/review",
                                        "recipe/{recipeId}/review/{reviewId}",
                                        "/users/my-profile"
                                )
                                .authenticated()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/users/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users/my-profile")
                                .failureUrl("/users/login?fail") //로그인실패시 이동할 url
                                .permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/users/login")
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))
                )

                .logout(
                        logout -> logout
                                //  .logoutUrl("")
                                .logoutSuccessUrl("/users/login")
                );

        return httpSecurity.build();
    }
}