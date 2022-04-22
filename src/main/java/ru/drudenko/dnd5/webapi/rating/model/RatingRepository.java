package ru.drudenko.dnd5.webapi.rating.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.drudenko.dnd5.webapi.profile.model.Profile;

@Transactional
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
