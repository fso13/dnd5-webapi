package ru.drudenko.dnd5.webapi.dto.event;

import org.springframework.context.ApplicationEvent;
import ru.drudenko.dnd5.webapi.dto.user.ResetPasswordDto;

public class ResetPasswordEvent extends ApplicationEvent {

    public ResetPasswordEvent(ResetPasswordDto resetPasswordDto) {
        super(resetPasswordDto);
    }

}
