package mb;

import domain.Tipaviona;
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
public class MbTipAviona {

    List<Tipaviona> tipovi;
    Tipaviona odabraniTip;
    
    /**
     * Creates a new instance of MbTipAviona
     */
    public MbTipAviona() {
    }

    @PostConstruct
    public void inicijalizujPodatke(){
        tipovi = KontrolerWS.getInstance().vratiTipove();
    }
    
    public List<Tipaviona> getTipovi() {
        return tipovi;
    }

    public void setTipovi(List<Tipaviona> tipovi) {
        this.tipovi = tipovi;
    }

    public Tipaviona getOdabraniTip() {
        return odabraniTip;
    }

    public void setOdabraniTip(Tipaviona odabraniTip) {
        this.odabraniTip = odabraniTip;
    }
    
}
