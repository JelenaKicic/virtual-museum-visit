package com.museum.backend.models.entities;

import com.museum.backend.models.enums.LogType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "log")
public class LogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "time", nullable = false)
    private Timestamp time;
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = true, length = 512)
    private LogType action;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public LogType getAction() {
        return action;
    }

    public void setAction(LogType action) {
        this.action = action;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
