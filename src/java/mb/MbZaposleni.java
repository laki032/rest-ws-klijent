package mb;

import domain.Zaposleni;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import ws.klijent.kontroler.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@RequestScoped
public class MbZaposleni {

    List<Zaposleni> zaposleni;
    Zaposleni odabraniZaposleni;
    boolean izmena = false;
    
    /**
     * Creates a new instance of MbZaposleni
     */
    public MbZaposleni() {
    }

    @PostConstruct
    public void inicijalizujPodatke() {
        zaposleni = KontrolerWS.getInstance().vratiZaposlene();
    }

    public List<Zaposleni> getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(List<Zaposleni> zaposleni) {
        this.zaposleni = zaposleni;
    }

    public Zaposleni getOdabraniZaposleni() {
        return odabraniZaposleni;
    }

    public void setOdabraniZaposleni(Zaposleni odabraniZaposleni) {
        this.odabraniZaposleni = odabraniZaposleni;
    }

    public String pokreniIzmenu(Zaposleni z) {
        odabraniZaposleni = z;
        izmena = true;
        return "unosZaposlenih";
    }

}
