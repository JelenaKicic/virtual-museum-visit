package com.museum.backend.repositories;

import com.museum.backend.models.entities.UserTourEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserTourEntityRepository extends JpaRepository<UserTourEntity, Integer> {
    List<UserTourEntity> findByTourIdAndUserId(Integer tourId, Integer userId);
    List<UserTourEntity> findByUserId(Integer userId);
}
