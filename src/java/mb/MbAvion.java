package mb;

import domain.Avion;
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
    Avion odabraniAvion;
    Avion novi;
    
    
    /**
     * Creates a new instance of MbZaposleni
     */
    public MbAvion() {
        novi = new Avion();
    }
    
    @PostConstruct
    public void inicijalizujPodatke(){
        avioni = KontrolerWS.getInstance().vratiAvione();
    }
    
    public List<Avion> getAvioni() {
        return avioni;
    }

    public void setAvioni(List<Avion> avioni) {
        this.avioni = avioni;
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
            System.out.println("Avion:" + novi.getOznaka());
            KontrolerWS.getInstance().sacuvajNoviAvion(novi);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je sacuvan avion!!!", "Novi avion je sacuvan u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avion nije uspesno sacuvan!!!", ex.getMessage()));
        }
        return null;
    }
    
}
