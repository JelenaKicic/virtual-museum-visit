package com.museum.backend.services.impl;

import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.models.dto.MuseumDTO;
import com.museum.backend.models.dto.SingleMuseum;
import com.museum.backend.models.entities.MuseumEntity;
import com.museum.backend.models.requests.MuseumRequest;
import com.museum.backend.repositories.MuseumEntityRepository;
import com.museum.backend.services.MuseumService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MuseumServiceImpl implements MuseumService {
    private final ModelMapper modelMapper;
    private final MuseumEntityRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public MuseumServiceImpl(MuseumEntityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SingleMuseum findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), SingleMuseum.class);
    }

    @Override
    public MuseumEntity findEntityById(Integer id) throws NotFoundException {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }


    @Override
    public List<MuseumDTO> findByNameOrCityStart(String nameStart) {
        return repository.findByNameStartingWithOrCityStartingWith(nameStart, nameStart).stream().map(e -> modelMapper.map(e, MuseumDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MuseumDTO insert(MuseumRequest museumRequest){
        MuseumEntity museumEntity = modelMapper.map(museumRequest, MuseumEntity.class);
        museumEntity.setId(null);
        museumEntity = repository.saveAndFlush(museumEntity);
        entityManager.refresh(museumEntity);
        return modelMapper.map(museumEntity, MuseumDTO.class);
    }
}
