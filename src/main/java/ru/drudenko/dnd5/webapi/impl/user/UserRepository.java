package ru.drudenko.dnd5.webapi.impl.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
interface UserRepository {
    User save(User user);

    User findByUsername(String username);

    @Query("from User where username = :username OR email = :username")
    User findByUsernameOrEmail(@Param("username") String userName);

    Profile save(Profile profile);

    void deleteProfile(Profile profile);

    User findByEmail(String email);
}
