package com.museum.backend.models.dto;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class TourDTO {
    private Integer id;
    private Integer museumId;
    private Time startTime;
    private Date startDate;
    private Integer length;
    private String video;
    private double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMuseumId(Integer museumId) {
        this.museumId = museumId;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getMuseumId() {
        return museumId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Integer getLength() {
        return length;
    }

    public String getVideo() {
        return video;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourDTO tourDTO = (TourDTO) o;
        return id.equals(tourDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public double getPrice() {
        return price;
    }
}
