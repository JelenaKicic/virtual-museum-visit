package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import beans.TransactionBean;
import pools.ConnectionPool;

public class TransactionDao {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static ArrayList<TransactionBean> getAllTransactionsByCardId(int id) {
        ArrayList<TransactionBean> list = new ArrayList<TransactionBean>();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from transaction where card_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                list.add(new TransactionBean(
                    resultSet.getInt("id"),
                    resultSet.getInt("card_id"),
                    resultSet.getDouble("amount")
                ));
            }
        } catch (Exception e) {
        }
        return list;
    }


    public static void insertTransaction(TransactionBean transactionBean) {
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into transaction (card_id, amount) values (?,?)");
            preparedStatement.setInt(1, transactionBean.getCard_id());
            preparedStatement.setDouble(2, transactionBean.getAmount());

            preparedStatement.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

