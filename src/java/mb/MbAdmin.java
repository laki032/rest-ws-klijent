package mb;

import domain.Admin;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ws.client.Constants;
import ws.client.controller.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@SessionScoped
public class MbAdmin {

    Admin admin;
    Date vremeLogovanja;
    String theme;

    public MbAdmin() {
    }

    @PostConstruct
    public void init() {
        admin = new Admin();
        admin.setUlogovan(false);
        theme = "excite-bike";
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

    public String getTheme() {
        if (admin == null || admin.getTheme() == null || admin.getTheme().isEmpty()) {
            return theme;
        } else {
            return admin.getTheme();
        }
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String login() {
        try {
            vremeLogovanja = new Date();
            admin = KontrolerWS.getInstance().login(admin);
            theme = admin.getTheme();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MbTranslator.translate(Constants.SUCCESS), MbTranslator.translate(Constants.ADMIN_LOGIN_SUCCESS)));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MbTranslator.translate(Constants.FAILURE), MbTranslator.translate(ex.getMessage())));
        }
        return null;
    }

    public String logout() {
        try {
            theme = "excite-bike";
            String poruka = KontrolerWS.getInstance().logout(admin);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MbTranslator.translate(Constants.SUCCESS), MbTranslator.translate(poruka)));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MbTranslator.translate(Constants.FAILURE), MbTranslator.translate(ex.getMessage())));
        }
        admin = new Admin();
        admin.setUlogovan(false);
        return "index.xhtml";
    }
}
