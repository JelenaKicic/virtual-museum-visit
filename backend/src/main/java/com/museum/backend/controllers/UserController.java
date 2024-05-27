package com.museum.backend.controllers;


import com.museum.backend.exceptions.ConflictException;
import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.models.dto.CountResult;
import com.museum.backend.models.dto.UserDTO;
import com.museum.backend.models.enums.UserStatus;
import com.museum.backend.models.requests.SignUpRequest;
import com.museum.backend.services.UserService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody SignUpRequest signUpRequest) throws ChangeSetPersister.NotFoundException {
        if(userService.findByUsername(signUpRequest.getUsername()) != null) {
            throw new ConflictException("Username is taken!");
        }

        if(userService.findByEmail(signUpRequest.getEmail()) != null) {
            throw new ConflictException("Account with this email already exists!");
        }

        return userService.register(signUpRequest);
    }

    @PutMapping("/{id}/status/active")
    @ResponseStatus(HttpStatus.OK)
    public void activateUser(@PathVariable Integer id) throws NotFoundException {
        userService.changeStatus(id, UserStatus.ACTIVE);
    }

    @PutMapping("/{id}/status/block")
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(@PathVariable Integer id) throws NotFoundException {
        userService.changeStatus(id, UserStatus.BLOCKED);
    }

    @PutMapping("/{id}/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@PathVariable Integer id) throws NotFoundException {
        userService.resetPassword(id);
    }

    @GetMapping("/count/active")
    @ResponseStatus(HttpStatus.OK)
    public CountResult getActiveUsersCount() {
        return userService.getActiveUsersCount();
    }

    @GetMapping("/count/registered")
    @ResponseStatus(HttpStatus.OK)
    public CountResult getRegisteredUsersCount() {
        return userService.getRegisteredUsersCount();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}
