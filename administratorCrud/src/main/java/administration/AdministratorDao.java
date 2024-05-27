package administration;

import database.ConnectionPool;
import database.DAOUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministratorDao {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_GET_ALL_ADMINS = "SELECT * FROM user where role = \"ADMIN\"";
    private static final String SQL_IS_USERNAME_USED = "SELECT * FROM user WHERE username = ?";
    private static final String GET_BY_TOKEN = "SELECT * FROM user WHERE token = ?";
    private static final String GET_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "SELECT * FROM user WHERE username=? AND password=?";
    private static final String SQL_INSERT = "INSERT INTO user (username, password) VALUES (?,?)";
    private static final String DELETE_ADMIN = "DELETE FROM user WHERE id = ?";
    private static final String UPDATE_ADMIN = "UPDATE user SET username = ? WHERE id = ?";

    public static String hashedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(password.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static Administrator getUserByToken(String id){
        Administrator administrator = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            Object values[] = {id};
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
                    GET_BY_TOKEN, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()){
                administrator = new Administrator(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return administrator;
    }

    public static Administrator getUserById(String id){
        Administrator administrator = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            Object values[] = {id};
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
                    GET_BY_ID, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()){
                administrator = new Administrator(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return administrator;
    }

    public static ArrayList<Administrator> getAllAdministrators(){
        ArrayList<Administrator> administrators = new ArrayList();
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
                    SQL_GET_ALL_ADMINS, false);
            rs = pstmt.executeQuery();
            while (rs.next()){
                administrators.add(new Administrator(rs.getInt("id"), rs.getString("username"), rs.getString("password")));
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return administrators;
    }

    public static Administrator selectByUsernameAndPassword(String username, String password){
        Administrator administrator = null;
        Connection connection = null;
        ResultSet rs = null;
        try {
            Object values[] = {username, hashedPassword(password)};
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
                    SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()){
                administrator = new Administrator(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return administrator;
    }

    public static boolean isUserNameUsed(String username) {
        boolean result = true;
        Connection connection = null;
        ResultSet rs = null;
        Object values[] = {username};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection,
                    SQL_IS_USERNAME_USED, false, values);
            rs = pstmt.executeQuery();
            if (rs.next()){
                result = false;
            }
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

    public static boolean insert(Administrator administrator) {
        boolean result = false;
        Connection connection = null;
        ResultSet generatedKeys = null;
        try {
            Object values[] = { administrator.getUsername(), hashedPassword(administrator.getPassword()) };
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
            pstmt.executeUpdate();
            generatedKeys = pstmt.getGeneratedKeys();
            if(pstmt.getUpdateCount()>0) {
                result = true;
            }
            if (generatedKeys.next())
                administrator.setId(generatedKeys.getInt(1));
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

    public static boolean update(String username, String id) {
        boolean result = false;
        Connection connection = null;
        ResultSet generatedKeys = null;
        try {
            Object values[] = { username , id};
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, UPDATE_ADMIN, true, values);
            pstmt.executeUpdate();
            generatedKeys = pstmt.getGeneratedKeys();
            if(pstmt.getUpdateCount()>0) {
                result = true;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }


    public static boolean delete(String id) {
        boolean result = false;
        Connection connection = null;
        ResultSet generatedKeys = null;
        try {
            Object values[] = { id };
            connection = connectionPool.checkOut();
            PreparedStatement pstmt = DAOUtil.prepareStatement(connection, DELETE_ADMIN, true, values);
            pstmt.executeUpdate();
            generatedKeys = pstmt.getGeneratedKeys();
            if(pstmt.getUpdateCount()>0) {
                result = true;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }
}
