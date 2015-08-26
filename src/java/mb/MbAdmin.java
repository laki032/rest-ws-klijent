package mb;

import domain.Admin;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ws.klijent.kontroler.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@SessionScoped
public class MbAdmin {

    Admin admin;
    Date vremeLogovanja;

    /**
     * Creates a new instance of MbAdmin
     */
    public MbAdmin() {
    }

    @PostConstruct
    public void inicijalizujPodatke() {
        admin = new Admin();
        admin.setUlogovan(false);
    }

    public String getVremeLogovanja() {
        return new SimpleDateFormat("h:mm:ss").format(vremeLogovanja);
    }

    public void setVremeLogovanja(Date vremeLogovanja) {
        this.vremeLogovanja = vremeLogovanja;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void login() {
        admin.setUlogovan(true);
        vremeLogovanja = new Date();
//        try {
//            vremeLogovanja = new Date();
//            admin = KontrolerWS.getInstance().login(admin);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno ste se ulogovali!", ""));
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pogresan username i/ili password!!!", ex.getMessage()));
//        }
    }

    public void logout() {
//        try {
//            String poruka = KontrolerWS.getInstance().logout(admin);
//            admin = new Admin();
//            admin.setUlogovan(false);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, poruka, ""));
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska pri odjavljivanju!!!", ex.getMessage()));
//        }
        admin.setUlogovan(false);
    }
}
