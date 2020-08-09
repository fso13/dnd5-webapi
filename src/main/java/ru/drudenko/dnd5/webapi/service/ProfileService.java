package ru.drudenko.dnd5.webapi.service;

import org.springframework.transaction.annotation.Transactional;
import ru.drudenko.dnd5.webapi.dto.profile.ProfileDto;

@Transactional
public interface ProfileService {

    void createProfile(String username, ProfileDto profileDto);

    void setCurrentProfile(String username, ProfileDto profileDto);

    void deleteProfile(String username, ProfileDto profileDto);
}
