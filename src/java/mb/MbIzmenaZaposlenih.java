package mb;

import domain.Aviomehanicar;
import domain.Licenca;
import domain.Pilot;
import domain.Uloga;
import domain.Zaposleni;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import ws.client.Constants;
import ws.client.controller.KontrolerWS;

/**
 *
 * @author laki
 */
@ManagedBean
@SessionScoped
public class MbIzmenaZaposlenih implements Serializable {

    public enum Tip {
        PILOT, MEHANICAR
    }
    Tip tip;

    Uloga novaUloga;
    Licenca novaLicenca;
    Pilot pilot;
    Pilot odabraniPilot;
    Aviomehanicar mehanicar;
    Aviomehanicar odabraniMehanicar;

    public MbIzmenaZaposlenih() {
    }

    @PostConstruct
    public void init() {
        novaUloga = new Uloga();
        novaLicenca = new Licenca();
    }

    public Uloga getNovaUloga() {
        return novaUloga;
    }

    public void setNovaUloga(Uloga novaUloga) {
        this.novaUloga = novaUloga;
    }

    public Licenca getNovaLicenca() {
        return novaLicenca;
    }

    public void setNovaLicenca(Licenca novaLicenca) {
        this.novaLicenca = novaLicenca;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Aviomehanicar getMehanicar() {
        return mehanicar;
    }

    public void setMehanicar(Aviomehanicar mehanicar) {
        this.mehanicar = mehanicar;
    }

    public Pilot getOdabraniPilot() {
        return odabraniPilot;
    }

    public void setOdabraniPilot(Pilot odabraniPilot) {
        this.odabraniPilot = odabraniPilot;
    }

    public Aviomehanicar getOdabraniMehanicar() {
        return odabraniMehanicar;
    }

    public void setOdabraniMehanicar(Aviomehanicar odabraniMehanicar) {
        this.odabraniMehanicar = odabraniMehanicar;
    }

    public String pokreniIzmenu(Zaposleni zap) {
        if (zap instanceof Pilot) {
            odabraniPilot = (Pilot) zap;
            pilot = new Pilot(odabraniPilot);
            pilot.setDatumPregleda((odabraniPilot).getDatumPregleda());
            pilot.setOcenaStanja((odabraniPilot).getOcenaStanja());
            novaUloga = new Uloga();
            novaUloga.setPilot(pilot);
            tip = Tip.PILOT;
            return "izmenaPilota";
        }
        if (zap instanceof Aviomehanicar) {
            odabraniMehanicar = (Aviomehanicar) zap;
            mehanicar = new Aviomehanicar(odabraniMehanicar);
            mehanicar.setTipMehanicara((odabraniMehanicar).getTipMehanicara()); //umestp odabMeh mozda (Aviomehanicar) zap ???
            novaLicenca = new Licenca();
            novaLicenca.setAviomehanicar(mehanicar);
            tip = Tip.MEHANICAR;
            return "izmenaMehanicara";
        }
        return "pretragaZaposlenih";
    }

    public String sacuvajIzmenu() {
        String poruka = "";
        switch (tip) {
            case MEHANICAR:
                poruka = KontrolerWS.getInstance().edit(mehanicar);
                break;
            case PILOT:
                poruka = KontrolerWS.getInstance().edit(pilot);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MbTranslator.translate(poruka), ""));
        return "pretragaZaposlenih";
    }

    public String sacuvajNovuUlogu() {
        String poruka = KontrolerWS.getInstance().novaUloga(novaUloga);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MbTranslator.translate(poruka), ""));
        novaUloga = null;
        pokreniIzmenu(pilot);
        return "izmenaPilota";
    }

    public String sacuvajNovuLicencu() {
        String poruka = KontrolerWS.getInstance().novaLicenca(novaLicenca);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, MbTranslator.translate(poruka), ""));
        novaLicenca = null;
        pokreniIzmenu(mehanicar);
        return "izmenaMehanicara";
    }

    public List<String> vratiUloge() {
        List<String> l = new ArrayList<>();
        if (odabraniPilot == null) {
            l.add(MbTranslator.translate(Constants.PILOT_IS_NOT_CHOOSEN));
            return l;
        } else {
            try {
                if (odabraniPilot.getUlogaList().isEmpty()) {
                    throw new NullPointerException();
                }
                for (Uloga u : odabraniPilot.getUlogaList()) {
                    l.add(u.toString());
                }
            } catch (NullPointerException npe) {
                l.add(MbTranslator.translate(Constants.PILOT_DOES_NOT_HAVE_ACTIVITY));
            }
        }
        return l;
    }

    public List<String> vratiLicence() {
        List<String> l = new ArrayList<>();
        if (odabraniMehanicar == null) {
            l.add(MbTranslator.translate(Constants.MECHANIC_IS_NOT_CHOOSEN));
            return l;
        } else {
            try {
                if (odabraniMehanicar.getLicencaList().isEmpty()) {
                    throw new NullPointerException();
                }
                for (Licenca lc : odabraniMehanicar.getLicencaList()) {
                    l.add(lc.toString());
                }
            } catch (NullPointerException npe) {
                l.add(MbTranslator.translate(Constants.MECHANIC_DOES_NOT_HAVE_ACTIVITY));
            }
        }
        return l;
    }

}
