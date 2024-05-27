package com.museum.backend.models.dto;


import java.util.List;

public class SingleMuseum extends MuseumDTO{
    private List<TourDTO> tours;

    public List<TourDTO> getTours() {
        return tours;
    }

    public void setTours(List<TourDTO> tours) {
        this.tours = tours;
    }
}
