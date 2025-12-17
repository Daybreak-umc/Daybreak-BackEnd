package umc9th_hackathon.daybreak.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final String[] swaggerUris = {
            "/swagger-ui/**", "/swagger-ui.html",
            "/v3/api-docs/**", "/swagger-resources/**",
            "/webjars/**", "/healthcheck", "/chat"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(swaggerUris).permitAll()  // Swagger 완전 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()  // 개발용: 모든 요청 허용
                )
                .formLogin(form -> form.disable())  // 폼 로그인 제거
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
