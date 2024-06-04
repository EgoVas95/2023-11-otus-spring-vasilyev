package ru.otus.hw.mealconfigurator.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public String getCurrentUserId() {
        //return "user_1";
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth.getPrincipal() instanceof DefaultOidcUser user) {
            return user.getClaimAsString("sub");
        } else if (auth instanceof JwtAuthenticationToken jwt) {
            return jwt.getName();
        }
        return auth.getName();
    }
}
