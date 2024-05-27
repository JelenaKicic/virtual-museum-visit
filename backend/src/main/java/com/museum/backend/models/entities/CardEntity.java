package com.museum.backend.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "card")
public class CardEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic
    @Column(name = "surname", nullable = false, length = 45)
    private String surname;
    @Basic
    @Column(name = "number", nullable = false, length = 255)
    private String number;
    @Basic
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;
    @Basic
    @Column(name = "type", nullable = false, length = 45)
    private String type;
    @Basic
    @Column(name = "pin", nullable = false, length = 45)
    private String pin;
    @Basic
    @Column(name = "saldo", nullable = false, precision = 2)
    private BigDecimal saldo;
    @Basic
    @Column(name = "online_payment_enabled", nullable = false)
    private Boolean onlinePaymentEnabled;
    @OneToMany(mappedBy = "card")
    private List<TransactionEntity> transactions;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Boolean getOnlinePaymentEnabled() {
        return onlinePaymentEnabled;
    }

    public void setOnlinePaymentEnabled(Boolean onlinePaymentEnabled) {
        this.onlinePaymentEnabled = onlinePaymentEnabled;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
}
