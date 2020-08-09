package ru.drudenko.dnd5.webapi.dto.user;

import lombok.Data;
import ru.drudenko.dnd5.webapi.dto.profile.ProfileDto;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private String id;
    private String username;
    @Email
    private String email;
    private String password;
    private String passwordConfirm;
    private String tokenRegistration;
    private List<ProfileDto> profileDtos = new ArrayList<>();

    public ProfileDto getCurrentProfile() {
        return profileDtos.stream().filter(ProfileDto::isCurrent).findFirst().get();
    }
}
