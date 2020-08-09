package ru.drudenko.dnd5.webapi.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import ru.drudenko.dnd5.webapi.service.UserService;

public final class SecurityHelper {
    private SecurityHelper() {
    }

    public static String getUsernameAndAddProfilesAttributes(Model model, UserService userService) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            model.addAttribute("profiles", userService.findByUsername(username).getProfileDtos());
            return username;
        }
        return null;
    }
}
