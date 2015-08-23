package mb;

import domain.Avion;
import domain.Tipaviona;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import ws.klijent.kontroler.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@RequestScoped
public class MbAvion {

    List<Avion> avioni;
    List<Tipaviona> tipovi;
    Avion novi;
    boolean izmena = false;
    String krit;

    /**
     * Creates a new instance of MbAvion
     */
    public MbAvion() {
        novi = new Avion();
    }

    @PostConstruct
    public void inicijalizujPodatke() {
        avioni = KontrolerWS.getInstance().vratiAvione();
        tipovi = KontrolerWS.getInstance().vratiTipove();
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
            System.out.println("Novi avion: " + novi.getOznaka());
            KontrolerWS.getInstance().sacuvajNoviAvion(novi);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvan avion!!!", "Novi avion je sacuvan u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije uspesno sacuvan!!!", ex.getMessage()));
        }
        return null;
    }

    public String sacuvajIzmenuAviona(Avion a) {
        System.out.println("Izmena aviona: " + a.getOznaka());
        try {
            KontrolerWS.getInstance().sacuvajIzmenuAviona(a);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvana izmena aviona!!!", "Avion je sacuvan u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije uspesno sacuvan!!!", ex.getMessage()));
        }
        novi = new Avion();
        return "unosAviona";
    }

    public String pokreniIzmenu(Avion a) {
        novi = a;
        izmena = true;
        return "unosAviona";
    }

    public String obrisi(Avion a) {
        try {
            System.out.println("Brisanje aviona: " + a.getOznaka());
            KontrolerWS.getInstance().obrisiAvion(a);
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
