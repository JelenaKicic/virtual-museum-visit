package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import beans.CardBean;

import pools.ConnectionPool;
public class CardDAO {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void updateCreditCardBalance(double balance, int number) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update card set saldo=? where number=?");
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, number);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enableOrDisableOnlinePayments(boolean isBlocked, String number) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update card set online_payment_enabled=? where number=?");
            preparedStatement.setBoolean(1, isBlocked);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CardBean findCardByNumberAndPin(String number, String pin) {
        Connection connection = getConnection();
        CardBean creditCardBean = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from card where number=? and pin=?");
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                creditCardBean = new CardBean(
                    resultSet.getInt("id"),
                    resultSet.getString("number"),
                    resultSet.getString("type"),
                    resultSet.getString("pin"),
                    resultSet.getString("expiration_date"),
                    resultSet.getDouble("saldo"),
                    resultSet.getBoolean("blocked"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return creditCardBean;
    }
}

