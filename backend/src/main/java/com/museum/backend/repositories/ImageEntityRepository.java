package com.museum.backend.repositories;

import com.museum.backend.models.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Integer> {
}
