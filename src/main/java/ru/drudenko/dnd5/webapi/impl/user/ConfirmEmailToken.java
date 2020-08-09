package ru.drudenko.dnd5.webapi.impl.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tb_confirm_email_token")
class ConfirmEmailToken {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    private Instant expiryDate = Instant.now().plusSeconds(60 * 60 * 24);


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
