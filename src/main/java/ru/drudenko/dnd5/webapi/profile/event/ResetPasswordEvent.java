package ru.drudenko.dnd5.webapi.profile.event;

import org.springframework.context.ApplicationEvent;
import ru.drudenko.dnd5.webapi.profile.dto.ResetPasswordDto;

public class ResetPasswordEvent extends ApplicationEvent {

    public ResetPasswordEvent(ResetPasswordDto resetPasswordDto) {
        super(resetPasswordDto);
    }

}
