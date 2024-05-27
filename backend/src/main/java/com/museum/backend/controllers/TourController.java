package com.museum.backend.controllers;

import com.museum.backend.models.dto.JwtUserDTO;
import com.museum.backend.models.dto.SingleTour;
import com.museum.backend.models.dto.TourDTO;
import com.museum.backend.models.requests.TourRequest;
import com.museum.backend.services.TourService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.museum.backend.util.Constants.FILE_UPLOAD_LOCATION;

@RestController
@RequestMapping("")
public class TourController {
    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping("/tours/museums/{museumId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TourDTO createTour(@PathVariable Integer museumId, @Valid @RequestBody TourRequest tourRequest) {
        return tourService.insert(tourRequest, museumId);
    }

    @GetMapping("/tours/museums/{museumId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TourDTO> findByMuseumId(@PathVariable Integer museumId) {
        return tourService.findByMuseumId(museumId);
    }

    @GetMapping("/tours/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingleTour getSingleTour(@PathVariable Integer id, Authentication auth) {
        JwtUserDTO user = (JwtUserDTO) auth.getPrincipal();
        return tourService.findTour(id, user.getId());
    }

    @PostMapping("/tours/{id}/upload-files")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles, @PathVariable Integer id) {
        return tourService.uploadFiles(multipartFiles, id);
    }

    @PostMapping("/tours/{id}/buy")
    @ResponseStatus(HttpStatus.OK)
    public void buyTour(@PathVariable Integer id, Authentication auth) {
        JwtUserDTO user = (JwtUserDTO) auth.getPrincipal();
        tourService.buyTour(id, user.getId());
    }

    @GetMapping("/tours_active")
    @ResponseStatus(HttpStatus.OK)
    public List<SingleTour> getActiveBoughtTours(Authentication auth) {
        JwtUserDTO user = (JwtUserDTO) auth.getPrincipal();
        return tourService.getActiveBoughtTours(user.getId());
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(FILE_UPLOAD_LOCATION + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
