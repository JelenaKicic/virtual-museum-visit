package com.museum.backend.models.dto;

public class ActionCountDTO {
    private String period;
    private Integer count;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ActionCountDTO() {
    }

    public ActionCountDTO(String period, Integer count) {
        this.period = period;
        this.count = count;
    }
}
