package me.projects.piccy.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {

    @Bean
    public SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("api/posts/create", "api/posts/toggleLike/*").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "api/posts/*").authenticated()
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .formLogin(config -> config
                        .loginProcessingUrl("/api/login")
                        .loginPage("/login")
                        .successHandler(
                                (request, response, authentication) -> response.sendRedirect("/")
                        )
                        .failureHandler(
                                (request, response, exception) -> {
                                    if (exception instanceof BadCredentialsException) response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    else response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                                }
                        )
                )
                .logout(config -> config.logoutUrl("/api/logout"))
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    AuthenticationProvider getAuthenticationProvider(PasswordEncoder passwordEncoder, AuthUserService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder);
        provider.setUserDetailsService(userService);

        return provider;
    }
}
