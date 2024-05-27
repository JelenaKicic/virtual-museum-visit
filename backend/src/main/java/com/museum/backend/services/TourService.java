package com.museum.backend.services;

import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.models.dto.SingleTour;
import com.museum.backend.models.dto.TourDTO;
import com.museum.backend.models.requests.TourRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourService {
    public SingleTour findTour(Integer tourId, Integer userId) throws NotFoundException;
    public List<TourDTO> findByMuseumId(Integer nameStart);
    public List<String> uploadFiles(List<MultipartFile> multipartFiles, Integer tourId);
    public TourDTO insert(TourRequest tourRequest, Integer museumId);
    public void buyTour(Integer tourId, Integer userId);
    public List<SingleTour> getActiveBoughtTours(Integer userId);
}
