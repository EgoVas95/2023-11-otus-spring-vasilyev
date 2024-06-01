package ru.otus.hw.mealconfigurator.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                // Разрешаем запросы со всех доменов
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOriginPatterns(Collections.singletonList("*"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
                    corsConfig.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                // Настройка доступа к конечным точкам
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("PRODUCT_read")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("PRODUCT_write")
                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasAuthority("PRODUCT_write")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("PRODUCT_write")
                        .anyRequest().authenticated())
                .oauth2Client(Customizer.withDefaults())
                .build();
    }
}
