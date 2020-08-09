package ru.drudenko.dnd5.webapi.service;

import ru.drudenko.dnd5.webapi.dto.user.ResetPasswordDto;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;

public interface NotificationService {
    void sendResetPassword(ResetPasswordDto resetPasswordDto);

    void sendRegistration(UserDto userDto);
}
