package ru.drudenko.dnd5.webapi.impl.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.drudenko.dnd5.webapi.dto.event.RegistrationEvent;
import ru.drudenko.dnd5.webapi.dto.event.ResetPasswordEvent;
import ru.drudenko.dnd5.webapi.dto.user.ResetPasswordDto;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;
import ru.drudenko.dnd5.webapi.service.NotificationService;


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
