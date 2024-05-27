package com.museum.backend.models.dto;

import java.util.Objects;

public class ImageDTO {
    private Integer id;
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageDTO)) return false;
        ImageDTO imageDTO = (ImageDTO) o;
        return id.equals(imageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setImage(String image) {
        this.image = image;
    }
}
