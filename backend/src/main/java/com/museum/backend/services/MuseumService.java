package com.museum.backend.services;

import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.models.dto.MuseumDTO;
import com.museum.backend.models.dto.SingleMuseum;
import com.museum.backend.models.entities.MuseumEntity;
import com.museum.backend.models.requests.MuseumRequest;

import java.util.List;

public interface MuseumService {
    public SingleMuseum findById(Integer id) throws NotFoundException;
    public List<MuseumDTO> findByNameOrCityStart(String nameStart);
    public MuseumDTO insert(MuseumRequest museumRequest);
    public MuseumEntity findEntityById(Integer id) throws NotFoundException;
}

