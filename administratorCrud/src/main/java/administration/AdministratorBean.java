package administration;

import java.io.Serializable;
import java.util.ArrayList;

public class AdministratorBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Administrator administrator = new Administrator();
    private boolean isLoggedIn = false;

    public boolean login(String username, String password) {
        if ((administrator = AdministratorDao.selectByUsernameAndPassword(username, password)) != null) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void logout() {
        administrator = new Administrator();
        isLoggedIn = false;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public boolean isUserNameAllowed(String username) {
        return AdministratorDao.isUserNameUsed(username);
    }

    public boolean create(String username, String password) {
        return AdministratorDao.insert(new Administrator(username, password));
    }

    public boolean update(String username, String id) {
        return AdministratorDao.update(username, id);
    }

    public boolean delete(String id) {
        return AdministratorDao.delete(id);
    }

    public Administrator getUserByToken(String id) {
        return AdministratorDao.getUserByToken(id);
    }

    public Administrator getUserById(String id) {
        return AdministratorDao.getUserById(id);
    }

    public ArrayList<Administrator> getAllAdministrators() {
        return AdministratorDao.getAllAdministrators();
    }
}
