package ru.otus.hw.mealconfigurator.logic;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    public String getCurrentUserSub() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser user = (DefaultOidcUser) auth.getPrincipal();
            return user.getClaimAsString("sub");
        }
        return null;
    }
}
