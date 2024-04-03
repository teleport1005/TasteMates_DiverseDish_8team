package TasteMates.DiverseDish.auth.config;

import TasteMates.DiverseDish.oauth.OAuth2SuccessHandler;
import TasteMates.DiverseDish.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers(
//
//                                )
//                                .anonymous()
                            .requestMatchers(
                                    "/users/login",
                                    "/users/profiles",
                                    "/users/signup"
                            )
                            .permitAll()
                                .requestMatchers(
                                        "/users/info",
                                        "/users/update",
                                        "/users/{userId}/updateImg",
                                        "/recipe",
                                        "/recipe/{id}",
                                        "/recipe/{id}/cook_order",
                                        "recipe/{recipeId}/review",
                                        "recipe/{recipeId}/review/{reviewId}",
                                        "/users/profiles"
                                )
                                .authenticated()
                                .anyRequest()
                                .permitAll()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/users/login")
                                .loginProcessingUrl("/login")//Form태그의 actionUrl과 동일하게 맞추기
                                .defaultSuccessUrl("/users/profiles")
                                .failureUrl("/users/login?fail") //로그인실패시 이동할 url
//                                .permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/users/login")
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))
                )

                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                );

        return httpSecurity.build();
    }
}