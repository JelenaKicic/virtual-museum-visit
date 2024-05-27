package beans;

import dao.TransactionDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;


public class TransactionBean implements Serializable {

    private int id;
    private int card_id;
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionBean() {
        super();
    }

    public TransactionBean(int id, int card_id, double amount) {
        this.id = id;
        this.card_id = card_id;
        this.amount = amount;
    }


    public static ArrayList<TransactionBean> getAllByCardId(int number){
        return TransactionDao.getAllTransactionsByCardId(number);
    }
}

