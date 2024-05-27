package com.museum.backend.services;

import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.models.dto.CountResult;
import com.museum.backend.models.dto.UserDTO;
import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.UserStatus;
import com.museum.backend.models.requests.SignUpRequest;

import java.util.List;

public interface UserService {
    public UserDTO findById(Integer id) throws NotFoundException;
    public void logout(Integer userId);
    public UserDTO register(SignUpRequest signUpRequest) throws NotFoundException;
    public UserDTO findByEmail(String email);
    public UserDTO findByUsername(String username);
    public void setUserToken(Integer id, String token);
    public void changeStatus(Integer userId, UserStatus request);
    public void resetPassword(Integer userId);
    public UserDTO insert(UserEntity userEntity);
    public CountResult getActiveUsersCount();
    public CountResult getRegisteredUsersCount();
    public List<UserDTO> getAllUsers();
}
