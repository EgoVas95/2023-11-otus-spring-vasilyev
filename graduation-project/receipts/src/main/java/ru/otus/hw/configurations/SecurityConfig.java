package ru.otus.hw.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String GROUPS = "groups";

    private static final String REALM_ACCESS_CLAIM = "realm_access";

    private static final String ROLES_CLAIM = "roles";

    private final KeycloakLogoutHandler keycloakLogoutHandler;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
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
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAuthority("PRODUCT_read")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("PRODUCT_write")
                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasAuthority("PRODUCT_write")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("PRODUCT_write")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(config -> config.jwt(Customizer.withDefaults()))
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.addLogoutHandler(
                        keycloakLogoutHandler).logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
        return authorities -> getAthorities(authorities.iterator().next());
    }

    private Set<GrantedAuthority> getAthorities(GrantedAuthority authority) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        if (authority instanceof OidcUserAuthority oidcUserAuthority) {
            var userInfo = oidcUserAuthority.getUserInfo();
            if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
                var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
                var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
                mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
            } else if (userInfo.hasClaim(GROUPS)) {
                Collection<String> roles = userInfo.getClaim(GROUPS);
                mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
            }
        } else {
            var oauth2UserAuthority = (OAuth2UserAuthority) authority;
            Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

            if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
                Map<String, Object> realmAccess = (Map<String, Object>) userAttributes.get(
                        REALM_ACCESS_CLAIM);
                Collection<String> roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
                mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
            }
        }
        return mappedAuthorities;
    }

    private Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(
                Collectors.toList());
    }
}