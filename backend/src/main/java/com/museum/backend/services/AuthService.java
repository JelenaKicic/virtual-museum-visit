package com.museum.backend.services;

import com.museum.backend.models.dto.UserDTO;
import com.museum.backend.models.requests.LoginRequest;

public interface AuthService {
    UserDTO login(LoginRequest request);
}
