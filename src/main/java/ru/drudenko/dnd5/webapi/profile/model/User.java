package ru.drudenko.dnd5.webapi.profile.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_users")
public class User {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String username;
    @Email
    private String email;
    private String password;
    @Transient
    private String passwordConfirm;

    @Column(name = "confirm_email")
    private boolean confirmEmail = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Profile> profiles = new HashSet<>();

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
