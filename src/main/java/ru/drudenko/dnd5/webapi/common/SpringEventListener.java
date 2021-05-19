package ru.drudenko.dnd5.webapi.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.drudenko.dnd5.webapi.notification.NotificationService;
import ru.drudenko.dnd5.webapi.profile.dto.ResetPasswordDto;
import ru.drudenko.dnd5.webapi.profile.dto.UserDto;
import ru.drudenko.dnd5.webapi.profile.event.RegistrationEvent;
import ru.drudenko.dnd5.webapi.profile.event.ResetPasswordEvent;


@RequiredArgsConstructor
@Component
public class SpringEventListener {
    private final NotificationService notificationService;

    @Async
    @EventListener
    public void onApplicationEvent(RegistrationEvent event) {
        notificationService.sendRegistration((UserDto) event.getSource());
    }

    @EventListener
    public void onApplicationEvent(ResetPasswordEvent event) {
        notificationService.sendResetPassword((ResetPasswordDto) event.getSource());
    }

}
