package ru.drudenko.dnd5.webapi.profile;

public interface SecurityService {

    void autoLogin(String username, String password);

    String validatePasswordResetToken(String id, String token);

}
