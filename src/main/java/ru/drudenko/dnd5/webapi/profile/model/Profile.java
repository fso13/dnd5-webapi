package ru.drudenko.dnd5.webapi.profile.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Setter
@Entity
@Cacheable
@Table(name = "tb_profiles")
public
class Profile {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    @Column
    private String name;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "is_current")
    private boolean current;


    @Column(name = "time_create", updatable = false)

    private Instant createTime;

    @Column(name = "time_update")

    private Instant updateTime;

    @PrePersist
    private void save() {
        this.createTime = Instant.now();
        this.updateTime = Instant.now();
    }

    @PreUpdate
    private void update() {
        this.updateTime = Instant.now();
    }
}
