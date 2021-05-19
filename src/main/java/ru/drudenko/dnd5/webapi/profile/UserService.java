package ru.drudenko.dnd5.webapi.profile;

import org.springframework.transaction.annotation.Transactional;
import ru.drudenko.dnd5.webapi.profile.dto.UserDto;

@Transactional
public interface UserService {
    void registration(UserDto user);

    UserDto findByUsername(String username);

    void reset(UserDto user);

    UserDto confirm(String hashToken);

    UserDto findByEmail(String email);

    void saveNewPassword(UserDto user);
}
