package ru.drudenko.dnd5.webapi.impl.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.drudenko.dnd5.webapi.dto.event.RegistrationEvent;
import ru.drudenko.dnd5.webapi.dto.event.ResetPasswordEvent;
import ru.drudenko.dnd5.webapi.dto.profile.ProfileDto;
import ru.drudenko.dnd5.webapi.dto.user.ResetPasswordDto;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;
import ru.drudenko.dnd5.webapi.service.ProfileService;
import ru.drudenko.dnd5.webapi.service.UserService;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService, ProfileService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordResetTokenRepository passwordTokenRepository;
    private final ConfirmEmailTokenRepository confirmEmailTokenRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserMapper userMapper = UserMapper.MAPPER;

    @Override
    public void registration(UserDto userDto) {
        var confirmEmailToken = new ConfirmEmailToken();
        //todo переписать регистрацию
        var user = save(userDto);

        var profile = new Profile();
        profile.setName("Default");
        profile.setUser(user);
        profile.setUserId(user.getId());
        profile.setCurrent(true);
        userRepository.save(profile);

        confirmEmailToken.setUser(user);
        confirmEmailToken = confirmEmailTokenRepository.save(confirmEmailToken);

        userDto.setTokenRegistration(confirmEmailToken.getId());
        applicationEventPublisher.publishEvent(new RegistrationEvent(userDto));
    }

    @Override
    public void saveNewPassword(UserDto userDto) {

        var user = userRepository.findByUsername(userDto.getUsername());
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void createProfile(String username, ProfileDto profileDto) {
        var byUsername = userRepository.findByUsername(username);

        var profile = new Profile();
        profile.setName(profileDto.getName());
        profile.setUserId(byUsername.getId());
        profile.setCurrent(false);
        byUsername.getProfiles().add(profile);
        userRepository.save(byUsername);
    }

    @Transactional
    @Override
    public void setCurrentProfile(String username, ProfileDto profileDto) {
        var byUsername = userRepository.findByUsername(username);
        byUsername.getProfiles().forEach(profile -> {
            profile.setCurrent(profileDto.getName().equals(profile.getName()));
            userRepository.save(profile);
        });
    }

    @Transactional
    @Override
    public UserDto confirm(String hashToken) {
        var confirmEmailToken = confirmEmailTokenRepository.findById(hashToken).orElse(null);
        if (confirmEmailToken == null) {
            throw new IllegalArgumentException("Токен не найден.");
        }

        if (confirmEmailToken.getExpiryDate().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Ссылка устарела.");
        }

        confirmEmailToken.getUser().setConfirmEmail(true);
        userRepository.save(confirmEmailToken.getUser());
        return userMapper.fromEntity(confirmEmailToken.getUser());
    }

    @Override
    public UserDto findByEmail(String email) {
        var byUsername = userRepository.findByEmail(email);
        var userDto = userMapper.fromEntity(byUsername);
        if (userDto != null) {
            userDto.setProfileDtos(byUsername.getProfiles().stream().map(userMapper::fromEntity).collect(Collectors.toList()));
        }
        return userDto;
    }

    @Transactional
    @Override
    public void deleteProfile(String username, ProfileDto profileDto) {
        var byUsername = userRepository.findByUsername(username);
        var profile = byUsername.getProfiles().stream()
                .filter(profile1 -> profile1.getName().equals(profileDto.getName()) && !profile1.getName().equals("Default"))
                .findAny().get();


        if (profile.isCurrent()) {
            var defaultProfile = byUsername.getProfiles().stream()
                    .filter(profile1 -> profile1.getName().equals("Default"))
                    .findAny().get();

            defaultProfile.setCurrent(true);
            userRepository.save(defaultProfile);
        }

        userRepository.deleteProfile(profile);
        byUsername.getProfiles().remove(profile);
        userRepository.save(byUsername);

    }

    private User save(UserDto userDto) {
        var user = userRepository.findByUsername(userDto.getUsername());
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        if (user == null) {
            return userRepository.save(userMapper.fromDto(userDto));
        } else {
            user.setPassword(userDto.getPassword());
            return userRepository.save(user);
        }
    }

    @Override
    public UserDto findByUsername(String username) {
        var byUsername = userRepository.findByUsername(username);
        var userDto = userMapper.fromEntity(byUsername);
        if (userDto != null) {
            userDto.setProfileDtos(byUsername.getProfiles().stream().map(userMapper::fromEntity).collect(Collectors.toList()));
        }
        return userDto;
    }

    @Override
    public void reset(UserDto userDto) {
        var user = userRepository.findByUsernameOrEmail(userDto.getUsername());
        if (user == null) {
            return;
        }

        var myToken = new PasswordResetToken();
        myToken.setUser(user);
        myToken = passwordTokenRepository.save(myToken);
        applicationEventPublisher.publishEvent(new ResetPasswordEvent(new ResetPasswordDto(userMapper.fromEntity(user), myToken.getId())));
    }
}
