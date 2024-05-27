package com.museum.backend.services;

import com.itextpdf.text.DocumentException;
import com.museum.backend.models.dto.ActionCountDTO;
import com.museum.backend.models.dto.LogDTO;
import com.museum.backend.models.requests.LogRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface LogService {
    public LogDTO saveLog(LogRequest visitor);
    public List<LogDTO> getAll();
    public File getLogsAsPDF() throws IOException, DocumentException;
    public List<ActionCountDTO> actionsStatistics();
}
