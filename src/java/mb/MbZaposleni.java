package mb;

import domain.Aviomehanicar;
import domain.Pilot;
import domain.Zaposleni;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    String tip; //tip zaposlenog koji se unosi
    String tipNovogMehanicara;
    boolean ocenaStanjaNovog;
    Date datumNovog;
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

    public String getTip() {
        return tip;
    }

    public Date getDatumNovog() {
        return datumNovog;
    }

    public String getTipNovogMehanicara() {
        return tipNovogMehanicara;
    }

    public boolean isOcenaStanjaNovog() {
        return ocenaStanjaNovog;
    }

    public void setDatumNovog(Date datumNovog) {
        this.datumNovog = datumNovog;
    }

    public void setOcenaStanjaNovog(boolean ocenaStanjaNovog) {
        this.ocenaStanjaNovog = ocenaStanjaNovog;
    }

    public void setTipNovogMehanicara(String tipNovogMehanicara) {
        this.tipNovogMehanicara = tipNovogMehanicara;
    }

    public void setTip(String tip) {
        this.tip = tip;
        if (tip.equals("Pilot")) {
            novi = new Pilot(novi);
            ocenaStanjaNovog = false;
            datumNovog = new Date();
        } else {
            novi = new Aviomehanicar(novi);
            tipNovogMehanicara = "";
        }
    }

    public boolean isIzmena() {
        return izmena;
    }

    public void setIzmena(boolean izmena) {
        this.izmena = izmena;
    }

    public void dodajNovog() {
        if (tip.equals("Pilot")) {
            ((Pilot) novi).setDatumPregleda(datumNovog);
            ((Pilot) novi).setOcenaStanja(ocenaStanjaNovog);
        } else {
            ((Aviomehanicar) novi).setTipMehanicara(tipNovogMehanicara);
        }
        dodatiZaposleni.add(novi);
        setTip("");
        novi = new Zaposleni();
    }

    public boolean noviJeMehanicar() {
        if (tip == null) {
            return false;
        }
        return tip.equals("Avio mehanicar") || tip.equals("Avio-mehanicar");
    }

    public boolean noviJePilot() {
        if (tip == null) {
            return false;
        }
        return tip.equals("Pilot");
    }

    public String sacuvajSve() {
        try {
            System.out.println("Cuvanje liste zaposlenih");
            KontrolerWS.getInstance().sacuvajSveZaposlene(dodatiZaposleni);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacija uspesna!!!", "Zaposleni su sacuvani u bazi podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zaposleni nisu uspesno sacuvani!!!", ex.getMessage()));
        }
        return "unosZaposlenih";
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

    public String vratiOstalePodatkeOZaposlenom(Zaposleni z) {
        if (z instanceof Pilot) {
            String datum = new SimpleDateFormat("dd/MM/yyyy").format(((Pilot) z).getDatumPregleda());
            String ocena = ((Pilot) z).getOcenaStanja() ? "sposoban" : "nesposoban";
            return datum + " ocenjen kao " + ocena;
        } else {
            return ((Aviomehanicar) z).getTipMehanicara();
        }
    }

}
