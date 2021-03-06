package ru.drudenko.dnd5.webapi.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
    private UserDto userDto;
    private String tokenId;
}
