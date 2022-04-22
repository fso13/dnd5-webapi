package ru.drudenko.dnd5.webapi.rating.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import ru.drudenko.dnd5.webapi.monster.model.Monster;
import ru.drudenko.dnd5.webapi.profile.model.User;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "create_seq", sequenceName = "seq_entity", allocationSize = 1)
@Table(name = "tb_ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "create_seq")
    private Long id;
    @Column
    private String nick;
    @Column
    private int quest;
    @Column
    private int xp;
    @Column
    private Instant date;
    @PrePersist
    private void save() {
        this.date = Instant.now();
    }
}