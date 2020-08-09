package ru.drudenko.dnd5.webapi.impl.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        try {
            return (User) entityManager.createQuery("select u from User as u where u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }

    }

    @Override
    public User findByUsernameOrEmail(String userName) {
        try {
            return (User) entityManager.createQuery("select u from User as u where u.username = :username OR u.email = :username")
                    .setParameter("username", userName)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return (User) entityManager.createQuery("select u from User as u where u.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Profile save(Profile profile) {
        entityManager.persist(profile);
        return profile;
    }

    @Transactional
    @Override
    public void deleteProfile(Profile profile) {
        entityManager.createQuery("delete  from Profile where id =:id").setParameter("id", profile.getId()).executeUpdate();
    }
}
