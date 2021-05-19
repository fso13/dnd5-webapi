package ru.drudenko.dnd5.webapi.notification;

import ru.drudenko.dnd5.webapi.profile.dto.ResetPasswordDto;
import ru.drudenko.dnd5.webapi.profile.dto.UserDto;

public interface NotificationService {
    void sendResetPassword(ResetPasswordDto resetPasswordDto);

    void sendRegistration(UserDto userDto);
}
