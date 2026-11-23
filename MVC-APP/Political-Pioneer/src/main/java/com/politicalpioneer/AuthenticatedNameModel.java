package com.politicalpioneer;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public final class AuthenticatedNameModel {

    private AuthenticatedNameModel() {
    }

    public static void addNameIfAuthenticated(Model model, Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("name", authentication.getName());
        }
    }
}
