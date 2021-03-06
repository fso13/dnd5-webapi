package ru.drudenko.dnd5.webapi.profile.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.drudenko.dnd5.webapi.profile.SecurityService;
import ru.drudenko.dnd5.webapi.profile.mapper.UserMapper;
import ru.drudenko.dnd5.webapi.profile.model.PasswordResetTokenRepository;

import java.time.Instant;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
class SecurityServiceImpl implements SecurityService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordResetTokenRepository passwordTokenRepository;
    private final UserMapper userMapper = UserMapper.MAPPER;

    @Override
    public void autoLogin(String username, String password) {
        var userDetails = userDetailsService.loadUserByUsername(username);
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            log.info("Auto login {} successfully!", username);
        }
    }

    @Override
    @Transactional
    public String validatePasswordResetToken(String id, String token) {
        var passToken =
                passwordTokenRepository.findById(token).orElse(null);
        if (passToken == null || passToken.getUser() == null || !passToken.getUser().getId().equals(id)) {
            return "invalidToken";
        }

        if (passToken.getExpiryDate().isBefore(Instant.now())) {
            return "expired";
        }

        var user = passToken.getUser();
        var userDto = userMapper.fromEntity(user);
        var auth = new UsernamePasswordAuthenticationToken(
                userDto, null, Collections.singletonList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}
