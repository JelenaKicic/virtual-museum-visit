package com.museum.backend.services.impl;


import com.museum.backend.models.dto.JwtUserDTO;
import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.UserStatus;
import com.museum.backend.repositories.UserEntityRepository;
import com.museum.backend.services.JwtUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final UserEntityRepository repository;
    private final ModelMapper modelMapper;

    public JwtUserDetailsServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper) {
        this.repository = userEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public JwtUserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findByUsernameAndStatus(username, UserStatus.ACTIVE);

        if(userEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        return modelMapper.map(userEntity, JwtUserDTO.class);
    }
}