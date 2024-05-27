package com.museum.backend.models.requests;


import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

public class TourRequest {
    @NotNull
    private Date startDate;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private Integer length;
    private String video;
    @NotNull
    private Double price;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
