package com.museum.backend.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "museum")
public class MuseumEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic
    @Column(name = "phone_number", nullable = false, length = 45)
    private String phoneNumber;
    @Basic
    @Column(name = "city", nullable = false, length = 255)
    private String city;
    @Basic
    @Column(name = "country", nullable = false, length = 255)
    private String country;
    @Basic
    @Column(name = "latitude", nullable = false, precision = 6)
    private BigDecimal latitude;
    @Basic
    @Column(name = "longitude", nullable = false, precision = 6)
    private BigDecimal longitude;
    @Basic
    @Column(name = "type", nullable = false, length = 255)
    private String type;
    @Basic
    @Column(name = "region", nullable = true, length = 255)
    private String region;
    @OneToMany(mappedBy = "museum")
    private List<TourEntity> tours;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<TourEntity> getTours() {
        return tours;
    }

    public void setTours(List<TourEntity> tours) {
        this.tours = tours;
    }
}
