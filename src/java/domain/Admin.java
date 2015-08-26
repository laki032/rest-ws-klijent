package domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Admin implements Serializable {

    private String username;
    private String password;
    private Date lastLogin;
    private boolean ulogovan;

    public Admin() {
    }

    public Admin(String username) {
        this.username = username;
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean getUlogovan() {
        return ulogovan;
    }

    public void setUlogovan(boolean ulogovan) {
        this.ulogovan = ulogovan;
    }

}
