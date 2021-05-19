package ru.drudenko.dnd5.webapi.profile.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.drudenko.dnd5.webapi.profile.ProfileService;
import ru.drudenko.dnd5.webapi.profile.UserService;
import ru.drudenko.dnd5.webapi.profile.dto.ProfileDto;

@Controller
@RequestMapping("profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("/add")
    public String addProfiles(Model model) {
        model.addAttribute("profile", new ProfileDto());
        var username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("profiles", userService.findByUsername(username).getProfileDtos());
        return "add-profile";
    }

    @GetMapping
    public String getProfiles(Model model) {
        var username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        model.addAttribute("profiles", userService.findByUsername(username).getProfileDtos());

        return "get-profiles";
    }

    @PostMapping
    public String postProfiles(@ModelAttribute("profile") ProfileDto profileDto) {
        var username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        profileService.createProfile(username, profileDto);
        return "redirect:/profiles";
    }

    @GetMapping("/{id}/delete")
    public String deleteProfile(@PathVariable("id") String name) {
        var username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        var profileDto = new ProfileDto();
        profileDto.setName(name);
        profileService.deleteProfile(username, profileDto);
        return "redirect:/profiles";
    }

    @ApiOperation(value = "Переключиться на другой профиль")
    @PatchMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void setCurrentProfile(@PathVariable("name") String name) {
        var username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        var profileDto = new ProfileDto();
        profileDto.setName(name);
        profileDto.setCurrent(true);
        profileService.setCurrentProfile(username, profileDto);
    }
}
