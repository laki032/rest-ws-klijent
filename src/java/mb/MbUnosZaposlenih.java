package mb;

import domain.Aviomehanicar;
import domain.Pilot;
import domain.Zaposleni;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import ws.client.controller.KontrolerWS;

/**
 *
 * @author laki
 */
@ManagedBean
@SessionScoped
public class MbUnosZaposlenih {

    public final String PILOT = "Pilot";
    public final String MEHANICAR = "Avio mehanicar";

    public String getMEHANICAR() {
        return MEHANICAR;
    }

    public String getPILOT() {
        return PILOT;
    }

    Zaposleni novi;
    Zaposleni odabraniZaposleni;
    String tip; //tip zaposlenog koji se unosi
    List<Zaposleni> dodatiZaposleni; //lista dodatih zap. koji treba da se sacuvaju u bazi

    public MbUnosZaposlenih() {
    }

    @PostConstruct
    public void init() {
        novi = new Zaposleni();
        dodatiZaposleni = new LinkedList<>();
    }

    public Zaposleni getNovi() {
        return novi;
    }

    public void setNovi(Zaposleni novi) {
        this.novi = novi;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
        switch (tip) {
            case PILOT:
                novi = new Pilot(novi);
                return;
            case MEHANICAR:
                novi = new Aviomehanicar(novi);
        }
    }

    public List<Zaposleni> getDodatiZaposleni() {
        return dodatiZaposleni;
    }

    public void setDodatiZaposleni(List<Zaposleni> dodatiZaposleni) {
        this.dodatiZaposleni = dodatiZaposleni;
    }

    public Zaposleni getOdabraniZaposleni() {
        return odabraniZaposleni;
    }

    public void setOdabraniZaposleni(Zaposleni odabraniZaposleni) {
        this.odabraniZaposleni = odabraniZaposleni;
    }

    public void dodajNovog() {
        dodatiZaposleni.add(novi);
        setTip("");
        novi = new Zaposleni();
    }

    public boolean noviJeMehanicar() {
        if (tip == null || tip.isEmpty()) {
            return false;
        }
        return tip.equals(MEHANICAR);
    }

    public boolean noviJePilot() {
        if (tip == null || tip.isEmpty()) {
            return false;
        }
        return tip.equals(PILOT);
    }

    public String sacuvajSve() {
        try {
            String poruka = KontrolerWS.getInstance().saveAll(dodatiZaposleni);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacija uspesna!!!", poruka));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zaposleni nisu uspesno sacuvani!!!", ex.getMessage()));
        }
        dodatiZaposleni = new LinkedList<>();
        return "unosZaposlenih";
    }

    public String vratiOstalePodatkeOZaposlenom(Zaposleni z) {
        if (z instanceof Pilot) {
            String datum = new SimpleDateFormat("dd/MM/yyyy").format(((Pilot) z).getDatumPregleda());
            String ocena = ((Pilot) z).getOcenaStanja() ? "sposoban" : "nesposoban";
            return datum + " ocenjen kao " + ocena;
        }
        if (z instanceof Aviomehanicar) {
            return ((Aviomehanicar) z).getTipMehanicara();
        }
        return z.toString();
    }

    public void obrisiSelektovaniRed() {
        if (odabraniZaposleni != null) {
            dodatiZaposleni.remove(odabraniZaposleni);
        }
    }

}
