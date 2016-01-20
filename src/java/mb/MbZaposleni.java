package mb;

import domain.Aviomehanicar;
import domain.Licenca;
import domain.Pilot;
import domain.Uloga;
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
    List<Zaposleni> dodatiZaposleni; //lista dodatih zap. koji treba da se sacuvaju u bazi
    Zaposleni novi;
    String tip; //tip zaposlenog koji se unosi
    String tipNovogMehanicara;
    boolean ocenaStanjaNovog;
    Date datumNovog;
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
        dodatiZaposleni = new LinkedList<>();
        ul_Ucitane = false;
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
        zaposleni = KontrolerWS.getInstance().getEmployees();
        return "unosZaposlenih";
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
