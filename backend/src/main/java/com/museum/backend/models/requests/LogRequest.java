package com.museum.backend.models.requests;


import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.LogType;

import java.sql.Timestamp;

public class LogRequest {
    private Timestamp time;
    private LogType action;
    private UserEntity user;

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

    public LogRequest(Timestamp time, LogType action, UserEntity user) {
        this.time = time;
        this.action = action;
        this.user = user;
    }

}