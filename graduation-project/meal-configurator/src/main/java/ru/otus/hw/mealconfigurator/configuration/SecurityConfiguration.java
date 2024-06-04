package ru.otus.hw.mealconfigurator.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, "/api/**").hasRole("read-role")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("write-role")
                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasRole("write-role")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("write-role")
                        .requestMatchers("/").permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Client(Customizer.withDefaults())
                .build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
                    getAuthorites(mappedAuthorities, userInfo.getClaim("realm_access"));
                }
            });
            return mappedAuthorities;
        };
    }

    private void getAuthorites(Set<GrantedAuthority> mappedAuthorities,
                               Map<String, Object> realmAccess) {
        Collection<String> realmRoles;
        if (realmAccess != null
                && (realmRoles = (Collection<String>) realmAccess.get("roles")) != null) {
            realmRoles
                    .forEach(role -> mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        }
    }
}
