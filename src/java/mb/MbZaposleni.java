package mb;

import domain.Zaposleni;
import java.util.ArrayList;
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
@ViewScoped // view scoped jer request scoped svaki put pravi novu listu dodatih zap.
public class MbZaposleni {

    List<Zaposleni> zaposleni; //lista zaposlenih u bazi
    List<Zaposleni> dodatiZaposleni; //lista dodatih zap. koji treba da se sacuvaju u bazi
    Zaposleni novi;
    Zaposleni odabraniZaposleni; // selektovani zap.
    boolean izmena = false;
    
    /**
     * Creates a new instance of MbZaposleni
     */
    public MbZaposleni() {
    }

    @PostConstruct
    public void inicijalizujPodatke() {
        novi = new Zaposleni();
        zaposleni = KontrolerWS.getInstance().vratiZaposlene();
        dodatiZaposleni = new ArrayList<>();
    }

    public List<Zaposleni> getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(List<Zaposleni> zaposleni) {
        this.zaposleni = zaposleni;
    }

    public void setDodatiZaposleni(List<Zaposleni> dodatiZaposleni) {
        this.dodatiZaposleni = dodatiZaposleni;
    }

    public List<Zaposleni> getDodatiZaposleni() {
        return dodatiZaposleni;
    }

    public Zaposleni getNovi() {
        return novi;
    }

    public void setNovi(Zaposleni odabraniZaposleni) {
        this.novi = odabraniZaposleni;
    }

    public Zaposleni getOdabraniZaposleni() {
        return odabraniZaposleni;
    }

    public void setOdabraniZaposleni(Zaposleni odabraniZaposleni) {
        this.odabraniZaposleni = odabraniZaposleni;
    }
    
    public String pokreniIzmenu(Zaposleni z) {
        /*novi = z;
        izmena = true;
        return "unosZaposlenih";*/
        // izmena ce se raditi u drugom panelu gde ce moguce biti da se doda i uloga/licenca
        return null;
    }
    
    public void dodajNovog(){
        dodatiZaposleni.add(novi);
        novi = new Zaposleni();
    }

}
