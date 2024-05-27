package com.museum.backend.models.dto;

import java.util.List;

public class SingleTour extends TourDTO {
    private List<ImageDTO> images;

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }
}
