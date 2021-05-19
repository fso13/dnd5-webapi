package ru.drudenko.dnd5.webapi.profile.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public
interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    @Query("from User where username = :username OR email = :username")
    User findByUsernameOrEmail(@Param("username") String userName);

    User findByEmail(String email);
}
