package mb;

import domain.Aviomehanicar;
import domain.Licenca;
import domain.Pilot;
import domain.Uloga;
import domain.Zaposleni;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import ws.client.controller.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@ManagedBean
@SessionScoped
public class MbZaposleni {

    List<Zaposleni> zaposleni; //lista zaposlenih u bazi
    Zaposleni novi;
    Zaposleni odabraniZaposleni; // selektovani zap.
    boolean ul_Ucitane;
    Uloga novaUloga;
    Licenca novaLicenca;

    public MbZaposleni() {
    }

    @PostConstruct
    public void init() {
        novi = new Zaposleni();
        zaposleni = KontrolerWS.getInstance().getEmployees();
        ul_Ucitane = false;
    }

    public List<Zaposleni> getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(List<Zaposleni> zaposleni) {
        this.zaposleni = zaposleni;
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

    public boolean isUl_Ucitane() {
        return ul_Ucitane;
    }

    public void setUl_Ucitane(boolean ul_Ucitane) {
        this.ul_Ucitane = ul_Ucitane;
    }

    public Licenca getNovaLicenca() {
        return novaLicenca;
    }

    public Uloga getNovaUloga() {
        return novaUloga;
    }

    public void setNovaLicenca(Licenca novaLicenca) {
        this.novaLicenca = novaLicenca;
    }

    public void setNovaUloga(Uloga novaUloga) {
        this.novaUloga = novaUloga;
    }

    public String pokreniIzmenu(Zaposleni zap) {
        odabraniZaposleni = zap;
        if (odabraniZaposleni instanceof Pilot) {
            novi = new Pilot(odabraniZaposleni);
            ((Pilot) novi).setDatumPregleda(((Pilot) odabraniZaposleni).getDatumPregleda());
            ((Pilot) novi).setOcenaStanja(((Pilot) odabraniZaposleni).getOcenaStanja());
            novaUloga = new Uloga();
            novaUloga.setPilot((Pilot)novi);
        } else {
            novi = new Aviomehanicar(odabraniZaposleni);
            ((Aviomehanicar) novi).setTipMehanicara(((Aviomehanicar) odabraniZaposleni).getTipMehanicara());
            novaLicenca = new Licenca();
            novaLicenca.setAviomehanicar((Aviomehanicar)novi);
        }
        return "izmenaZaposlenih";
    }

    public String sacuvajIzmenu() {
        try {
            String poruka = KontrolerWS.getInstance().edit(novi);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacija uspesna!!!", poruka));
            zaposleni = KontrolerWS.getInstance().getEmployees();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zaposleni nisu uspesno sacuvani!!!", ex.getMessage()));
        }
        return "pretragaZaposlenih";
    }

    public String obrisi(Zaposleni zap) {
        try {
            KontrolerWS.getInstance().remove(zap);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspesno je obrisan zaposleni!!!", "Zaposleni je obrisan iz baze podataka"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zaposleni nije obrisan!!!", ex.getMessage()));
        }
        return "pretragaZaposlenih";
    }

    public void zaposleniJeOdabran(SelectEvent event) {
        odabraniZaposleni = (Zaposleni) event.getObject();
        if (((Zaposleni) event.getObject()) instanceof Pilot) {
            Pilot p = new Pilot(((Zaposleni) event.getObject()).getJmbg());
            ((Pilot) odabraniZaposleni).setUlogaList(KontrolerWS.getInstance().vratiListuUlogaZaPilota(p));
        } else {
            Aviomehanicar a = new Aviomehanicar(((Zaposleni) event.getObject()).getJmbg());
            ((Aviomehanicar) odabraniZaposleni).setLicencaList(KontrolerWS.getInstance().vratiListuLicenciZaMehanicara(a));
        }
        ul_Ucitane = true;
    }

    public List<String> vratiUlogeLicence() {
        List<String> l = new ArrayList<>();
        if (odabraniZaposleni == null) {
            l.add("zaposleni nije odabran");
            return l;
        } else {
            try {
                if (odabraniZaposleni instanceof Pilot) {
                    for (Uloga u : ((Pilot) odabraniZaposleni).getUlogaList()) {
                        l.add(u.toString());
                    }
                } else {
                    for (Licenca lc : ((Aviomehanicar) odabraniZaposleni).getLicencaList()) {
                        l.add(lc.toString());
                    }
                }
            } catch (NullPointerException npe) {
                l.add("odabrani zaposleni nije nijednom rasporedjen");
            }
        }
        return l;
    }
    
    public String sacuvajNovuUlogu(){
        String poruka = KontrolerWS.getInstance().novaUloga(novaUloga);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodata nova uloga", poruka));
        novaUloga = null;
        pokreniIzmenu(odabraniZaposleni);
        return "izmenaZaposlenih";
    }
    
    public String sacuvajNovuLicencu(){
        String poruka = KontrolerWS.getInstance().novaLicenca(novaLicenca);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodata nova licenca", poruka));
        novaLicenca = null;
        pokreniIzmenu(odabraniZaposleni);
        return "izmenaZaposlenih";
    }

}
