package mb;

import domain.Avion;
import domain.Tipaviona;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import ws.klijent.kontroler.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@ViewScoped
public class MbAvion {

    List<Avion> avioni;
    List<Tipaviona> tipovi;
    Avion odabraniAvion;
    Avion novi;

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

    public Avion getOdabraniAvion() {
        return odabraniAvion;
    }

    public void setOdabraniAvion(Avion odabraniAvion) {
        this.odabraniAvion = odabraniAvion;
    }

    public Avion getNovi() {
        return novi;
    }

    public void setNovi(Avion novi) {
        this.novi = novi;
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

}
