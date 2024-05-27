package com.museum.backend.controllers;

import com.museum.backend.exceptions.BadRequestException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.museum.backend.util.Constants.FILE_UPLOAD_LOCATION;

@RestController
@RequestMapping("/files")
public class FileUploadController {
    @PostMapping("/upload/tours/{tourId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles, @PathVariable Integer tourId) {
        File directory = new File(String.valueOf(FILE_UPLOAD_LOCATION));

        if (!directory.exists()) {
            directory.mkdir();
        }

        List<String> filenames = new ArrayList<>();
        for(MultipartFile file : multipartFiles) {
            String filename = tourId + "_" + file.getOriginalFilename();
            File targetFile = new File(FILE_UPLOAD_LOCATION + filename);

            try(OutputStream outputStream = new FileOutputStream(targetFile)) {
                IOUtils.copy(file.getInputStream(), outputStream);
                if(file.getContentType().equals("video/mp4")) {

                } else {

                }
            } catch (IOException  e) {
                throw new BadRequestException();
            }

            filenames.add(filename);
        }

        return filenames;
    }
}
