package mb;

import domain.Avion;
import domain.Tipaviona;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import ws.client.controller.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@SessionScoped
public class MbAvion {

    List<Avion> avioni;
    List<Tipaviona> tipovi;
    Avion avion;
    boolean izmena = false;

    public MbAvion() {
        avion = new Avion();
    }

    @PostConstruct
    public void init() {
        avioni = KontrolerWS.getInstance().getPlanes();
        tipovi = KontrolerWS.getInstance().getTypes();
    }

    public List<Avion> getAvioni() {
        return avioni;
    }

    public void setAvioni(List<Avion> avioni) {
        this.avioni = avioni;
    }

    public List<Tipaviona> getTipovi() {
        return tipovi;
    }

    public void setTipovi(List<Tipaviona> tipovi) {
        this.tipovi = tipovi;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public boolean isIzmena() {
        return izmena;
    }

    public void setIzmena(boolean izmena) {
        this.izmena = izmena;
    }

    public String sacuvajNoviAvion() {
        try {
            KontrolerWS.getInstance().create(avion);
            avioni = KontrolerWS.getInstance().getPlanes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvan avion!!!", "Novi avion je sacuvan u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije uspesno sacuvan!!!", ex.getMessage()));
        }
        return null;
    }

    public String sacuvajIzmenuAviona(Avion a) {
        try {
            KontrolerWS.getInstance().edit(avion);
            avioni = KontrolerWS.getInstance().getPlanes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvana izmena aviona!!!", "Avion je sacuvan u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije uspesno sacuvan!!!", ex.getMessage()));
        }
        izmena = false;
        avion = new Avion();
        return "pretragaAviona";
    }

    public String pokreniIzmenu(Avion a) {
        avion = a;
        izmena = true;
        return "unosAviona";
    }

    public String obrisi(Avion a) {
        try {
            KontrolerWS.getInstance().remove(a);
            avioni = KontrolerWS.getInstance().getPlanes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je obrisan avion!!!", "Avion je obrisan iz baze podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije obrisan!!!", ex.getMessage()));
        }
        return "pretragaAviona";
    }

    public void avionJeOdabran(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Odabran je avion", "[" + ((Avion) event.getObject()).getAvionID()+ "] " + ((Avion) event.getObject()).getOznaka());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
