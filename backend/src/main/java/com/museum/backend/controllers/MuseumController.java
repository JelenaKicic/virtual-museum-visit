package com.museum.backend.controllers;

import com.museum.backend.models.dto.MuseumDTO;
import com.museum.backend.models.dto.SingleMuseum;
import com.museum.backend.models.requests.MuseumRequest;
import com.museum.backend.services.MuseumService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/museums")
public class MuseumController {
    private final MuseumService museumService;

    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MuseumDTO create(@Valid @RequestBody MuseumRequest museumRequest) {
        return museumService.insert(museumRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MuseumDTO> findByNameOrCityStart(@RequestParam(defaultValue = "") String nameStart) {
        return museumService.findByNameOrCityStart(nameStart);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingleMuseum getMuseumsById(@PathVariable Integer id) {
        return museumService.findById(id);
    }
}
