package com.museum.backend.services.impl;

import com.museum.backend.exceptions.UnauthorizedException;
import com.museum.backend.models.dto.JwtUserDTO;
import com.museum.backend.models.dto.UserDTO;
import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.LogType;
import com.museum.backend.models.enums.Role;
import com.museum.backend.models.requests.LogRequest;
import com.museum.backend.models.requests.LoginRequest;
import com.museum.backend.services.AuthService;
import com.museum.backend.services.LogService;
import com.museum.backend.services.UserService;
import com.museum.backend.util.LoggingUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final LogService logService;
    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;


    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, LogService logService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public UserDTO login(LoginRequest request) {
        UserDTO response = null;
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    );
            JwtUserDTO user = (JwtUserDTO) authenticate.getPrincipal();
            response = userService.findById(user.getId());
            String token = generateJwt(user);
            response.setToken(token);
            userService.setUserToken(user.getId(), token);
        } catch (Exception ex) {
            LoggingUtil.logException(ex, getClass());
            throw new UnauthorizedException("Username and/or password are not valid!");
        }

        if(response.getRole().equals(Role.USER)) {
            logService.saveLog(new LogRequest(new Timestamp(new Date().getTime()), LogType.LOGIN, new UserEntity(response.getId())));
        }

        return response;
    }

    private String generateJwt(JwtUserDTO user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }
}

