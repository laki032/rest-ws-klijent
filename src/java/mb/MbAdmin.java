package mb;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ws.klijent.kontroler.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@SessionScoped
public class MbAdmin {

    Date vremeLogovanja;
    boolean ulogovan;
    String ime;
    String pass;

    /**
     * Creates a new instance of MbAdmin
     */
    public MbAdmin() {
    }

    @PostConstruct
    public void inicijalizujPodatke() {
        ulogovan = false;
    }

    public String getIme() {
        return ime;
    }

    public boolean isUlogovan() {
        return ulogovan;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setUlogovan(boolean ulogovan) {
        this.ulogovan = ulogovan;
    }

    public String getVremeLogovanja() {
        return new SimpleDateFormat("h:mm:ss").format(vremeLogovanja);
    }

    public void setVremeLogovanja(Date vremeLogovanja) {
        this.vremeLogovanja = vremeLogovanja;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void login() {
        KontrolerWS.getInstance().login(ime, pass);
        vremeLogovanja = new Date();
        ulogovan = true;
    }

    public void logout() {
        KontrolerWS.getInstance().logout(ime, pass);
        ulogovan = false;
    }
}
