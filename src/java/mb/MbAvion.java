package mb;

import domain.Avion;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    
    /**
     * Creates a new instance of MbZaposleni
     */
    public MbAvion() {
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
    
}
