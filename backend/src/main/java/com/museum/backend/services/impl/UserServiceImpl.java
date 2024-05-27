package com.museum.backend.services.impl;

import com.museum.backend.exceptions.ConflictException;
import com.museum.backend.exceptions.ForbiddenException;
import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.models.dto.CountResult;
import com.museum.backend.models.dto.UserDTO;
import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.LogType;
import com.museum.backend.models.enums.Role;
import com.museum.backend.models.enums.UserStatus;
import com.museum.backend.models.requests.LogRequest;
import com.museum.backend.models.requests.SignUpRequest;
import com.museum.backend.repositories.UserEntityRepository;
import com.museum.backend.security.AuthorizationFilter;
import com.museum.backend.services.LogService;
import com.museum.backend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserEntityRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationFilter authorizationFilter;
    private final LogService logService;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${authorization.default.username:}")
    private String defaultUsername;
    @Value("${authorization.default.first-name:}")
    private String defaultName;
    @Value("${authorization.default.last-name:}")
    private String defaultSurname;
    @Value("${authorization.default.password:}")
    private String defaultPassword;
    @Value("${authorization.default.email:}")
    private String defaultEmail;
//    @Value("${spring.profiles.active:unknown}")
//    private String activeProfile;

    public UserServiceImpl(UserEntityRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthorizationFilter authorizationFilter, LogService logService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authorizationFilter = authorizationFilter;
        this.logService = logService;
    }

    @PostConstruct
    public void postConstruct() {
//        if (Constants.DATABASE_PROFILE.equals(activeProfile) && repository.count() == 0) {
        if (repository.count() == 0) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(defaultUsername);
            userEntity.setPassword(passwordEncoder.encode(defaultPassword));
            userEntity.setEmail(defaultEmail);
            userEntity.setName(defaultName);
            userEntity.setSurname(defaultSurname);
            userEntity.setStatus(UserStatus.ACTIVE);
            userEntity.setRole(Role.ADMIN);
            repository.saveAndFlush(userEntity);
        }
    }

    @Override
    public UserDTO findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), UserDTO.class);
    }

    public UserEntity findEntityById(Integer id) throws NotFoundException {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void setUserToken(Integer id, String token)  throws NotFoundException {
        UserEntity userEntity = findEntityById(id);
        userEntity.setToken(token);
        repository.saveAndFlush(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findByRole(Role.USER).stream().map(e -> modelMapper.map(e, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email);
        return userEntity != null ? modelMapper.map(userEntity, UserDTO.class) : null;
    }

    @Override
    public UserDTO findByUsername(String username) {
        UserEntity userEntity = repository.findByUsername(username);
        return userEntity != null ? modelMapper.map(userEntity, UserDTO.class) : null;
    }

    @Override
    public UserDTO register(SignUpRequest signUpRequest) throws NotFoundException {
        if (repository.existsByUsername(signUpRequest.getUsername())) {
            throw new ConflictException();
        }
        UserEntity entity = modelMapper.map(signUpRequest, UserEntity.class);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setStatus(UserStatus.REQUESTED);
        entity.setRole(Role.USER);
        UserDTO user = insert(entity);

        logService.saveLog(new LogRequest(new Timestamp(new Date().getTime()), LogType.REGISTER, new UserEntity(user.getId())));
        return user;
    }

    @Override
    public UserDTO insert(UserEntity userEntity) {
        userEntity.setId(null);
        userEntity = repository.saveAndFlush(userEntity);
        entityManager.refresh(userEntity);
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public void changeStatus(Integer userId, UserStatus status) {
        if (UserStatus.REQUESTED.equals(status))
            throw new ForbiddenException();
        UserEntity entity = findEntityById(userId);
        entity.setStatus(status);
        repository.saveAndFlush(entity);
    }

    @Override
    public void resetPassword(Integer userId) {
        UserEntity entity = findEntityById(userId);

        byte[] array = new byte[8]; // length is bounded by 7
        new Random().nextBytes(array);
        String newPassword = new String(array, Charset.forName("UTF-8"));

        entity.setPassword(passwordEncoder.encode(newPassword));
        repository.saveAndFlush(entity);
    }

    @Override
    public CountResult getActiveUsersCount() {
       List<UserEntity> users = repository.findByTokenNotNullAndRole(Role.USER);
       Integer activeUsersCount = 0;
       for(UserEntity user : users) {
           try {
               if (!authorizationFilter.isTokenExpired(user.getToken())) {
                   activeUsersCount++;
               }
           } catch (Exception ex) {

           }
       }
        return new CountResult(activeUsersCount.longValue());
    }

    @Override
    public CountResult getRegisteredUsersCount() {
        return new CountResult(repository.countByRole(Role.USER));
    }

    @Override
    public void logout(Integer userId) {
        UserEntity entity = findEntityById(userId);
        entity.setToken(null);
        repository.saveAndFlush(entity);
    }
}
