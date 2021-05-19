package ru.drudenko.dnd5.webapi.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.drudenko.dnd5.webapi.profile.dto.ProfileDto;
import ru.drudenko.dnd5.webapi.profile.dto.UserDto;
import ru.drudenko.dnd5.webapi.profile.model.Profile;
import ru.drudenko.dnd5.webapi.profile.model.User;

@Mapper
public
interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto fromEntity(User user);

    User fromDto(UserDto userDto);

    ProfileDto fromEntity(Profile profile);

    Profile fromDto(ProfileDto profileDto);
}
