package com.museum.backend.controllers;

import com.itextpdf.text.DocumentException;
import com.museum.backend.models.dto.ActionCountDTO;
import com.museum.backend.models.dto.LogDTO;
import com.museum.backend.services.LogService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {
    private final LogService logService;

    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<LogDTO> getAllLogs() {
        return logService.getAll();
    }

    @GetMapping(
        value = "/pdf",
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody byte[] getLogsAsPDF() throws IOException, DocumentException {
        File file = logService.getLogsAsPDF();
        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public List<ActionCountDTO> actionsStatistics() {
         return logService.actionsStatistics();
    }

}
