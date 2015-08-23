package mb;

import domain.Aviomehanicar;
import domain.Pilot;
import domain.Zaposleni;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
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
    String tip; //tip zaposlenog  koji se unosi
    Zaposleni odabraniZaposleni; // selektovani zap.
    String krit;
    boolean izmena = false;

    /**
     * Creates a new instance of MbZaposleni
     */
    public MbZaposleni() {
    }

    @PostConstruct
    public void inicijalizujPodatke() {
        novi = new Zaposleni();
        novi.setPilot(new Pilot());
        novi.setAviomehanicar(new Aviomehanicar());
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getKrit() {
        return krit;
    }

    public void setKrit(String krit) {
        this.krit = krit;
    }

    public boolean isIzmena() {
        return izmena;
    }

    public void setIzmena(boolean izmena) {
        this.izmena = izmena;
    }

    public void dodajNovog() {
        Zaposleni z = null;
        if (tip.equals("Pilot")) {
            z = new Pilot(novi.getJmbg());
            z.setPilot(novi.getPilot());
        } else {
            z = new Aviomehanicar(novi.getJmbg());
            z.setAviomehanicar(novi.getAviomehanicar());
        }
        z.setGodinaRodjenja(novi.getGodinaRodjenja());
        z.setImePrezime(novi.getImePrezime());
        dodatiZaposleni.add(z);
        novi = new Zaposleni();
    }

    public boolean noviJeMehanicar() {
        if (tip == null) {
            return false;
        }
        if (tip.equals("Avio mehanicar") || tip.equals("Avio-mehanicar")) {
            novi.setAviomehanicar(new Aviomehanicar());
            return true;
        }
        return false;
    }

    public boolean noviJePilot() {
        if (tip == null) {
            return false;
        }
        if (tip.equals("Pilot")) {
            novi.setPilot(new Pilot());
            return true;
        }
        return false;
    }

    public void sacuvajSve() {
        //metoda cuva sve iz liste unetih i prikazuje rezultat na ekranu
    }

    public String pokreniIzmenu(Zaposleni zap) {
        novi = zap;
        izmena = true;
        return "izmenaZaposlenih";
        // izmena ce se raditi u drugom panelu gde ce moguce biti da se doda i uloga/licenca
    }

    public String obrisi(Zaposleni zap) {
        try {
            System.out.println("Brisanje zaposlenog: " + zap.getJmbg());
            KontrolerWS.getInstance().obrisiZaposlenog(zap);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je obrisan zaposleni!!!", "Zaposleni je obrisan iz baze podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zaposleni nije obrisan!!!", ex.getMessage()));
        }
        return "pretragaZaposlenih";
    }

    public void zaposleniJeOdabran(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Odabran je zaposleni", "[" + ((Zaposleni) event.getObject()).getJmbg() + "] " + ((Zaposleni) event.getObject()).getImePrezime());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void obrisiSelektovaniRed() {
        dodatiZaposleni.remove(odabraniZaposleni);
    }

}
