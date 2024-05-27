package com.museum.backend.repositories;

import com.museum.backend.models.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourEntityRepository extends JpaRepository<TourEntity, Integer> {
    List<TourEntity> findByMuseumId(Integer museumId);
}
