package com.museum.backend.services.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.museum.backend.models.dto.ActionCountDTO;
import com.museum.backend.models.dto.LogDTO;
import com.museum.backend.models.entities.LogEntity;
import com.museum.backend.models.entities.UserEntity;
import com.museum.backend.models.enums.LogType;
import com.museum.backend.models.requests.LogRequest;
import com.museum.backend.repositories.LogEntityRepository;
import com.museum.backend.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LogServiceImpl implements LogService {
    private final ModelMapper modelMapper;
    private final LogEntityRepository repository;

    public LogServiceImpl(LogEntityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogDTO saveLog(LogRequest logRequest) {
        LogEntity logEntity = modelMapper.map(logRequest, LogEntity.class);
        return modelMapper.map(repository.save(logEntity), LogDTO.class);
    }

    @Override
    public List<LogDTO> getAll() {
        return repository.findAll().stream().map(e -> modelMapper.map(e, LogDTO.class)).collect(Collectors.toList());
    }

    @Override
    public File getLogsAsPDF() throws IOException, DocumentException {
        File file = new File("logs.pdf");
        file.createNewFile();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        PdfPTable table = new PdfPTable(3);
        addPdfTableHeader(table);

        List<LogDTO> logs = this.getAll();
        for(LogDTO log : logs) {
            addPdfRows(table, log);
        }

        document.add(table);
        document.close();

        return file;
    }

    @Override
    public List<ActionCountDTO> actionsStatistics() {
        Date date = new Date();

        Date dayBefore = new Date(date.getTime());
        dayBefore.setHours(date.getHours() - 24);

        List<LogEntity> logEntities = repository.findAllByActionWithTimeAfter(dayBefore, LogType.LOGIN);

        List<ActionCountDTO> actionCountDTOS = new ArrayList<>();
        for(int i = 0; i < 24; i++) {
            Date hourBefore = new Date(date.getTime());
            hourBefore.setHours(date.getHours() - 1);


            Integer count = 0;
            String timePeriod = hourBefore.getHours() + ":" + hourBefore.getMinutes() + " - " + date.getHours() + ":" + date.getMinutes();

            HashMap<Integer, UserEntity> userMap = new HashMap<>();
            for(LogEntity log : logEntities) {
                if((log.getTime().before(date) && hourBefore.before(log.getTime())) || (log.getTime().equals(date) || log.getTime().equals(hourBefore))) {
                    if(userMap.get(log.getUser().getId()) == null) {
                        userMap.put(log.getUser().getId(), log.getUser());
                        count++;
                    }
                }
            }

            actionCountDTOS.add(new ActionCountDTO(timePeriod, count));
            date = hourBefore;
        }

        return actionCountDTOS;
    }

    private void addPdfTableHeader(PdfPTable table) {
        Stream.of("Username", "Action", "Timestamp")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addPdfRows(PdfPTable table, LogDTO log) {
        table.addCell(log.getUser().getUsername());
        table.addCell(log.getAction().toString());
        table.addCell(log.getTime().toString());
    }
}
