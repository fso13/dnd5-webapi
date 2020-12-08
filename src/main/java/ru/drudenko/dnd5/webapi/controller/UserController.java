package ru.drudenko.dnd5.webapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;
import ru.drudenko.dnd5.webapi.service.SecurityService;
import ru.drudenko.dnd5.webapi.service.UserService;
import ru.drudenko.dnd5.webapi.utils.UserValidator;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    @GetMapping("/")
    public String home() {
        return "redirect:/spells";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.registration(userForm);
        return "redirect:/login";
    }

    @GetMapping("/confirm/{hashToken}")
    public String registration(@PathVariable(value = "hashToken") String hashToken, Model model, RedirectAttributes redirectAttributes) {

        try {
            userService.confirm(hashToken);
            model.addAttribute("message", "Почта подтверждена");
            redirectAttributes.addFlashAttribute("confirmEmail", "Почта подтверждена");
            return "redirect:/login?confirmEmail";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("expiredLink", e.getMessage());
            return "redirect:/login?expiredLink";
        }

    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout, String resetPassword) {
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        if (resetPassword != null) {
            model.addAttribute("resetPassword", "Вам отправленно письмо с ссылкой для востановления пароля.");
        }

        return "login";
    }

    @GetMapping("/forgotPassword")
    public String reset(Model model) {
        model.addAttribute("user", new UserDto());

        return "forgotPassword";
    }

    @GetMapping("/changePassword")
    public String reset(Model model, @RequestParam("id") String id, @RequestParam("token") String token) {
        var result = securityService.validatePasswordResetToken(id, token);
        if (result != null) {
            model.addAttribute("message", "auth.message." + result);
            return "redirect:/login";
        }
        return "redirect:/updatePassword";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(Model model) {
        model.addAttribute("user", new UserDto());
        return "updatePassword";
    }

    @PostMapping("/forgotPassword")
    public String reset(@ModelAttribute("user") UserDto userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "forgotPassword";
        }
        redirectAttributes.addFlashAttribute("resetPassword", "Вам отправленно письмо с ссылкой для востановления пароля.");
        userService.reset(userForm);
        return "redirect:/login?resetPassword";
    }

    @PostMapping("/savePassword")
    public String savePassword(Model model, UserDto newuser) {
        var user =
                ((UserDto) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal());

        var pw = newuser.getPassword();

        user.setPassword(newuser.getPassword());
        model.asMap().clear();
        userService.saveNewPassword(user);

        securityService.autoLogin(newuser.getUsername(), pw);
        return "redirect:/login";
    }
}
