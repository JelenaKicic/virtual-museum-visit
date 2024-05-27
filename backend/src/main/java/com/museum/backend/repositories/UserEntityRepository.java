package com.museum.backend.repositories;

import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.Role;
import com.museum.backend.models.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByUsernameAndPassword(String username, String password);
    public UserEntity findByUsernameAndStatus(String username, UserStatus status);
    public UserEntity findByUsername(String username);
    public UserEntity findByEmail(String email);
    public List<UserEntity> findByRole(Role role);
    Boolean existsByUsernameAndIdNot(String username, Integer id);
    Boolean existsByUsername(String username);
    Long countByRole(Role role);
    Long countByTokenNotNull();
    List<UserEntity> findByTokenNotNullAndRole(Role role);
}
