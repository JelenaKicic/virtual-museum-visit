package com.museum.backend.controllers;

import com.museum.backend.models.dto.JwtUserDTO;
import com.museum.backend.models.dto.UserDTO;
import com.museum.backend.models.requests.LoginRequest;
import com.museum.backend.services.AuthService;
import com.museum.backend.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService service;
    private final UserService userService;

    public AuthController(AuthService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("login")
    public UserDTO login(@RequestBody @Valid LoginRequest request) {
        return service.login(request);
    }

    @GetMapping("state")
    public UserDTO state(Authentication auth) {
        JwtUserDTO jwtUser = (JwtUserDTO) auth.getPrincipal();
        return userService.findById(jwtUser.getId());
    }

}
