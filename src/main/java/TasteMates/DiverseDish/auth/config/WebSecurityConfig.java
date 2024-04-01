package TasteMates.DiverseDish.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "",
                                "",
                                ""
                        )
                        .permitAll()
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
                                "recipe/{recipeId}/review/{reviewId}"
                        )
                        .authenticated()
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/users/login")
                                .defaultSuccessUrl("/users/info")
                                .failureUrl("")
                )
                .logout(
                        logout -> logout
                                .logoutUrl("")
                                .logoutSuccessUrl("/users/login")
                );

        return httpSecurity.build();
    }

}