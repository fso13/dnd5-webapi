package ru.drudenko.dnd5.webapi.impl.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.drudenko.dnd5.webapi.dto.profile.ProfileDto;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;

@Mapper
interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto fromEntity(User user);

    User fromDto(UserDto userDto);

    ProfileDto fromEntity(Profile profile);

    Profile fromDto(ProfileDto profileDto);
}
