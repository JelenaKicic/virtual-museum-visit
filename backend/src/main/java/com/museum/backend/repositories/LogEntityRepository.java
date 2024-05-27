package com.museum.backend.repositories;

import com.museum.backend.models.entities.LogEntity;
import com.museum.backend.models.enums.LogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LogEntityRepository extends JpaRepository<LogEntity, Integer> {
    @Query("select l from LogEntity l where l.time >= :time and l.action = :action")
    List<LogEntity> findAllByActionWithTimeAfter(@Param("time") Date time, @Param("action") LogType action);
}
