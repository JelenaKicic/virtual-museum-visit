package administration;

import java.io.Serializable;

public class Administrator implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;

    public Administrator(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Administrator() {
    }

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
