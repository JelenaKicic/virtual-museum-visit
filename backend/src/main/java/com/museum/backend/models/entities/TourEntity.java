package com.museum.backend.models.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "tour")
public class TourEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "start_time", nullable = false)
    private Time startTime;
    @Basic
    @Column(name = "length", nullable = false)
    private Integer length;
    @Basic
    @Column(name = "video", nullable = true, length = 255)
    private String video;
    @Column(name = "price", nullable = true)
    private Double price;
    @OneToMany(mappedBy = "tour")
    private List<ImageEntity> images;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "museum_id", referencedColumnName = "id", nullable = false)
    private MuseumEntity museum;

    public TourEntity() {
    }

    public TourEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public MuseumEntity getMuseum() {
        return museum;
    }

    public void setMuseum(MuseumEntity museum) {
        this.museum = museum;
    }
}
