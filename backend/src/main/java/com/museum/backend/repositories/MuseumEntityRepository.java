package com.museum.backend.repositories;

import com.museum.backend.models.entities.MuseumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuseumEntityRepository extends JpaRepository<MuseumEntity, Integer> {
    List<MuseumEntity> findByNameStartingWithOrCityStartingWith(String name, String city);
}
