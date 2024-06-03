package ru.otus.hw.mealconfigurator.services;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public String getCurrentUserId() {
        return "user_1";
        /*SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        if (auth.getPrincipal() instanceof DefaultOidcUser user) {
            return user.getClaimAsString("sub");
        }
        return null;*/
    }
}
