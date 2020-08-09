package ru.drudenko.dnd5.webapi.dto.event;

import org.springframework.context.ApplicationEvent;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;

public class RegistrationEvent extends ApplicationEvent {

    public RegistrationEvent(UserDto userDto) {
        super(userDto);
    }
}
