package com.museum.backend.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.museum.backend.exceptions.BadRequestException;
import com.museum.backend.exceptions.NotFoundException;
import com.museum.backend.models.dto.SingleTour;
import com.museum.backend.models.dto.TourDTO;
import com.museum.backend.models.dto.UserDTO;
import com.museum.backend.models.entities.*;
import com.museum.backend.models.enums.LogType;
import com.museum.backend.models.requests.LogRequest;
import com.museum.backend.models.requests.TourRequest;
import com.museum.backend.repositories.ImageEntityRepository;
import com.museum.backend.repositories.TourEntityRepository;
import com.museum.backend.repositories.UserTourEntityRepository;
import com.museum.backend.services.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.museum.backend.util.Constants.FILE_UPLOAD_LOCATION;

@Service
@Transactional
public class TourServiceImpl implements TourService {
    private final ModelMapper modelMapper;
    private final TourEntityRepository tourEntityRepository;
    private final UserTourEntityRepository userTourEntityRepository;
    private final ImageEntityRepository imageEntityRepository;
    private final MuseumService museumService;
    private final EmailService emailService;
    private final UserService userService;
    private TaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());
    private final LogService logService;

    @PersistenceContext
    private EntityManager entityManager;

    public TourServiceImpl(TourEntityRepository tourEntityRepository, UserTourEntityRepository userTourEntityRepository, ModelMapper modelMapper, ImageEntityRepository imageEntityRepository, MuseumService museumService, EmailService emailService, UserService userService, LogService logService) {
        this.tourEntityRepository = tourEntityRepository;
        this.userTourEntityRepository = userTourEntityRepository;
        this.modelMapper = modelMapper;
        this.imageEntityRepository = imageEntityRepository;
        this.museumService = museumService;
        this.emailService = emailService;
        this.userService = userService;
        this.logService = logService;
    }

    private Date createDate(Integer year, Integer month, Integer day, Integer hours, Integer minutes) {
        Date date = new Date();
        date.setYear(year);
        date.setMonth(month);
        date.setDate(day);
        date.setHours(hours);
        date.setMinutes(minutes);
        return date;
    }

    private boolean checkDateTimeIsInFuture(Date date, Time time) {
        Date dateWithTimeAdded = createDate(date.getYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes());
        return new Date().before(dateWithTimeAdded);
    }

    private boolean checkIfTourIsInProgress(Date tourStartDate, Time tourStartTime, int duration) {
        Date currentDate = new Date();

        Date dateWithTimeAdded = createDate(tourStartDate.getYear(), tourStartDate.getMonth(), tourStartDate.getDate(), tourStartTime.getHours() + duration, tourStartTime.getMinutes());

        boolean hasNotEnded = currentDate.before(dateWithTimeAdded);

        return hasNotEnded && !checkDateTimeIsInFuture(tourStartDate, tourStartTime);
    }

    public SingleTour findTour(Integer tourId, Integer userId) throws NotFoundException {
        SingleTour singleTour = modelMapper.map(tourEntityRepository.findById(tourId).orElseThrow(NotFoundException::new), SingleTour.class);
        List<UserTourEntity> userTourEntities = userTourEntityRepository.findByTourIdAndUserId(tourId, userId);
        if(!checkIfTourIsInProgress(singleTour.getStartDate(), singleTour.getStartTime(), singleTour.getLength()) || userTourEntities.size() == 0) {
            singleTour.setVideo(null);
            singleTour.setImages(new ArrayList<>());
        }
        return singleTour;
    }

    public List<TourDTO> findByMuseumId(Integer museumId) {
        return tourEntityRepository.findByMuseumId(museumId).stream().map(e -> modelMapper.map(e, TourDTO.class)).collect(Collectors.toList()).stream().filter(user -> {
            return checkDateTimeIsInFuture(user.getStartDate(), user.getStartTime());
        }).map(tour -> {
            tour.setVideo(null);
            return tour;
        }).collect(Collectors.toList());
    }

    public TourDTO insert(TourRequest tourRequest, Integer museumId) {
        MuseumEntity museumEntity = museumService.findEntityById(museumId);

        TourEntity tourEntity = modelMapper.map(tourRequest, TourEntity.class);
        tourEntity.setStartTime(Time.valueOf(tourRequest.getStartTime()));
        tourEntity.setId(null);
        tourEntity.setMuseum(museumEntity);
        tourEntity = tourEntityRepository.saveAndFlush(tourEntity);
        entityManager.refresh(tourEntity);
        System.out.println(tourEntity.getId());
        return modelMapper.map(tourEntity, TourDTO.class);
    }

    public TourEntity findEntityById(Integer id) throws NotFoundException {
        return tourEntityRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    private void checkNumberOfFiles(List<MultipartFile> multipartFiles) throws BadRequestException {
        if(multipartFiles.size() < 5 && multipartFiles.size() > 11) {
            throw new BadRequestException("Number of files is not right!");
        }
        int numOfImages = 0, numOfVideos = 0;
        for(MultipartFile file : multipartFiles) {
            if(file.getContentType().equals("video/mp4")) {
                numOfVideos++;
            } else if(file.getContentType().startsWith("image")) {
                numOfImages++;
            } else {
                throw new BadRequestException("File format is not right!");
            }
        }

        if(numOfVideos > 1) {
            throw new BadRequestException("Just one video can be uploaded!");
        }
        if(numOfImages < 5 || numOfImages > 10) {
            throw new BadRequestException("Upload between 5 and 10 images!");
        }
    }

    public List<String> uploadFiles(List<MultipartFile> multipartFiles, Integer tourId) throws NotFoundException, BadRequestException {
        TourEntity tour = findEntityById(tourId);

        checkNumberOfFiles(multipartFiles);

        for(ImageEntity image : tour.getImages()) {
            try {
                Files.delete(Paths.get(image.getImage()));
            } catch (IOException e) {
                throw new BadRequestException();
            }
            entityManager.remove(image);
        }

        File directory = new File(FILE_UPLOAD_LOCATION);

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
                    tour.setVideo(filename);
                    tourEntityRepository.saveAndFlush(tour);
                } else {
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setId(null);
                    imageEntity.setTour(tour);
                    imageEntity.setImage(filename);
                    imageEntityRepository.saveAndFlush(imageEntity);
                }
            } catch (IOException e) {
                throw new BadRequestException();
            }

            filenames.add(filename);
        }

        return filenames;
    }

    public List<SingleTour> getActiveBoughtTours(Integer userId) {
        List<SingleTour> tours = new ArrayList<>();
        List<UserTourEntity> userTourEntities = userTourEntityRepository.findByUserId(userId);

        for (UserTourEntity userTour : userTourEntities) {
            TourEntity tourEntity = userTour.getTour();
            if(checkIfTourIsInProgress(tourEntity.getStartDate(), tourEntity.getStartTime(), tourEntity.getLength())) {
                tours.add(modelMapper.map(tourEntity, SingleTour.class));
            }
        }
        return tours;
    }

    public void buyTour(Integer tourId, Integer userId) {
        Optional<TourEntity> tourOption = tourEntityRepository.findById(tourId);
        if(tourOption.isEmpty()) {
            throw new NotFoundException();
        }

        TourEntity tour = tourOption.get();
        UserDTO user = userService.findById(userId);

        List<UserTourEntity> userTourEntities = userTourEntityRepository.findByTourIdAndUserId(tourId, userId);
        if(userTourEntities.size() == 0) {
            UserTourEntity userTourEntity = new UserTourEntity();
            userTourEntity.setTour(tour);
            userTourEntity.setUser(new UserEntity(userId));
            userTourEntityRepository.saveAndFlush(userTourEntity);


            Date startDate = tour.getStartDate();
            Time startTime = tour.getStartTime();

            logService.saveLog(new LogRequest(new Timestamp(new Date().getTime()), LogType.BOUGHT_TOUR, new UserEntity(userId)));

            Date notificationHourBefore = createDate(startDate.getYear(), startDate.getMonth(), startDate.getDate(), startTime.getHours() - 1, startTime.getMinutes());
            if(notificationHourBefore.after(new Date())) {
                scheduleSendingNotificationEmail(
                        tour.getMuseum().getName() + " - tour " + tour.getId(),
                        user.getEmail(),
                        tour.getMuseum().getName() + " online tour number " + tour.getId() +  " starts in 1 hour.",
                        notificationHourBefore
                );
            }

            Date notification5MinutesBefore = createDate(startDate.getYear(), startDate.getMonth(), startDate.getDate(), startTime.getHours() + tour.getLength(), startTime.getMinutes()  - 5);
            if(notification5MinutesBefore.after(new Date())) {
                scheduleSendingNotificationEmail(
                        tour.getMuseum().getName() + " - tour " + tour.getId(),
                        user.getEmail(),
                        tour.getMuseum().getName() + " online tour number " + tour.getId() + " ends in 5 minutes.",
                        notification5MinutesBefore
                );
            }

            Date endOfTour = createDate(startDate.getYear(), startDate.getMonth(), startDate.getDate(), startTime.getHours() + tour.getLength(), startTime.getMinutes());
            scheduleUserLogout(userId, endOfTour);

            sendTicketAsPDF(tour, user.getEmail(), userTourEntity.getId());
        } else {
            throw new BadRequestException("User already purchased ticket!");
        }
    }

    private void sendTicketAsPDF(TourEntity tour, String email, Integer ticketNumber) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("ticket" + ticketNumber + ".pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);

            Paragraph title = new Paragraph("Ticket number " + ticketNumber, font);
            document.add(title);

            document.add(Chunk.NEWLINE);
            font.setSize(12);

            Paragraph content = new Paragraph(
                "You bought a ticket for virtual tour of " + tour.getMuseum().getName() + " museum" +
                        " for " + tour.getPrice() + "$." +
                        " Tour is starting " + tour.getStartDate() +
                        " at " + tour.getStartTime() + " and will last for " + tour.getLength() + " hours."
                    , font);
            document.add(content);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.close();

        emailService.sendEmail(
                "Confirmation of ticket purchase",
                email,
                "You bought a ticket for virtual tour of " + tour.getMuseum().getName() + " museum " +
                " for " + tour.getPrice() + "$." +
                " Tour is starting " + tour.getStartDate() +
                " at " + tour.getStartTime() + " and will last for " + tour.getLength() + " hours.",
                new File("ticket" + ticketNumber + ".pdf")
        );
    }

    private void scheduleSendingNotificationEmail(String subject, String recipient, String content, Date date) {
        scheduler.schedule(new Runnable() {
                   @Override
                   public void run() {
                       emailService.sendEmail(subject, recipient, content, null);
                   }
               },
                date
        );
    }

    private void scheduleUserLogout(Integer userId, Date date) {
        scheduler.schedule(new Runnable() {
                   @Override
                   public void run() {
                       userService.logout(userId);
                   }
               },
                date
        );
    }
}
