package ru.drudenko.dnd5.webapi.profile.event;

import org.springframework.context.ApplicationEvent;
import ru.drudenko.dnd5.webapi.profile.dto.UserDto;

public class RegistrationEvent extends ApplicationEvent {

    public RegistrationEvent(UserDto userDto) {
        super(userDto);
    }
}
