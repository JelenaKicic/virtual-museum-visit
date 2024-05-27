package beans;


import dao.CardDAO;

import java.io.Serializable;


public class CardBean implements Serializable{
    private int id;
    private String number;
    private String type;
    private String pin;
    private String expirationDate;
    private double saldo;
    private boolean onlinePaymentEnabled;
    private String firstname;
    private String lastname;
    private boolean isLoggedIn = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isOnlinePaymentEnabled() {
        return onlinePaymentEnabled;
    }

    public void setOnlinePaymentEnabled(boolean onlinePaymentEnabled) {
        CardDAO.enableOrDisableOnlinePayments(onlinePaymentEnabled, number);
        this.onlinePaymentEnabled = onlinePaymentEnabled;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public CardBean() {
        super();
    }

    public CardBean(int id, String number, String type, String pin, String expirationDate, double saldo, boolean onlinePaymentEnabled, String firstname, String lastname) {
        super();
        this.id = id;
        this.number = number;
        this.type = type;
        this.pin = pin;
        this.expirationDate = expirationDate;
        this.saldo = saldo;
        this.onlinePaymentEnabled = onlinePaymentEnabled;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public CardBean(String number, String type, String pin, String expirationDate, double saldo, boolean onlinePaymentEnabled, String firstname, String lastname) {
        super();
        this.number = number;
        this.type = type;
        this.pin = pin;
        this.expirationDate = expirationDate;
        this.saldo = saldo;
        this.onlinePaymentEnabled = onlinePaymentEnabled;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public static CardBean getCardBean(String number, String pin) {
        return CardDAO.findCardByNumberAndPin(number, pin);
    }
}

