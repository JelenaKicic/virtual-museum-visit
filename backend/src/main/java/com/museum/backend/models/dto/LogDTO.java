package com.museum.backend.models.dto;

import com.museum.backend.models.enums.LogType;

import java.sql.Timestamp;
import java.util.Objects;

public class LogDTO {
    private Integer id;
    private Timestamp time;
    private LogType action;
    private UserDTO user;

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogDTO)) return false;
        LogDTO logDTO = (LogDTO) o;
        return id.equals(logDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
