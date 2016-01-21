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
    Avion novi;
    boolean izmena = false;
    String krit;

    public MbAvion() {
        novi = new Avion();
    }

    @PostConstruct
    public void init() {
        avioni = KontrolerWS.getInstance().getPlanes();
        tipovi = KontrolerWS.getInstance().getTypes();
        krit = "";
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

    public Avion getNovi() {
        return novi;
    }

    public void setNovi(Avion novi) {
        this.novi = novi;
    }

    public boolean isIzmena() {
        return izmena;
    }

    public void setIzmena(boolean izmena) {
        this.izmena = izmena;
    }

    public String getKrit() {
        return krit;
    }

    public void setKrit(String krit) {
        this.krit = krit;
    }

    public String sacuvajNoviAvion() {
        try {
            KontrolerWS.getInstance().create(novi);
            avioni = KontrolerWS.getInstance().getPlanes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvan avion!!!", "Novi avion je sacuvan u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije uspesno sacuvan!!!", ex.getMessage()));
        }
        return null;
    }

    public String sacuvajIzmenuAviona(Avion a) {
        try {
            KontrolerWS.getInstance().edit(novi);
            avioni = KontrolerWS.getInstance().getPlanes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvana izmena aviona!!!", "Avion je sacuvan u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije uspesno sacuvan!!!", ex.getMessage()));
        }
        izmena = false;
        novi = new Avion();
        return "pretragaAviona";
    }

    public String pokreniIzmenu(Avion a) {
        novi = a;
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
