package mb;

import domain.Aviomehanicar;
import domain.Pilot;
import domain.Zaposleni;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    Zaposleni novi;
    Zaposleni odabraniZaposleni;
    String tip; //tip zaposlenog koji se unosi
    boolean ocenaStanjaNovog;
    Date datumNovog;
    String tipNovogMehanicara;
    List<Zaposleni> dodatiZaposleni; //lista dodatih zap. koji treba da se sacuvaju u bazi

    public MbUnosZaposlenih() {
    }
    
    @PostConstruct
    public void init() {
        novi = new Zaposleni();
        tip = "";
        tipNovogMehanicara = "";
        datumNovog = new Date();
        dodatiZaposleni = new ArrayList<>();
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
        if (tip.equals("Pilot")) {
            novi = new Pilot(novi);
            ocenaStanjaNovog = false;
            datumNovog = new Date();
        } else {
            novi = new Aviomehanicar(novi);
            tipNovogMehanicara = "";
        }
    }

    public boolean isOcenaStanjaNovog() {
        return ocenaStanjaNovog;
    }

    public void setOcenaStanjaNovog(boolean ocenaStanjaNovog) {
        this.ocenaStanjaNovog = ocenaStanjaNovog;
    }

    public Date getDatumNovog() {
        return datumNovog;
    }

    public void setDatumNovog(Date datumNovog) {
        this.datumNovog = datumNovog;
    }

    public String getTipNovogMehanicara() {
        return tipNovogMehanicara;
    }

    public void setTipNovogMehanicara(String tipNovogMehanicara) {
        this.tipNovogMehanicara = tipNovogMehanicara;
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
        } else {
            return ((Aviomehanicar) z).getTipMehanicara();
        }
    }

    public void obrisiSelektovaniRed() {
        dodatiZaposleni.remove(odabraniZaposleni);
    }

}
