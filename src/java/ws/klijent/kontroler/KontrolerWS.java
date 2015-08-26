/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.klijent.kontroler;

import domain.Admin;
import domain.Aviomehanicar;
import domain.Avion;
import domain.Licenca;
import domain.Pilot;
import domain.Tipaviona;
import domain.Uloga;
import domain.Zaposleni;
import java.util.List;
import javax.ws.rs.core.GenericType;
import ws.klijent.*;

public class KontrolerWS {

    private static KontrolerWS instance;
    private WSAvioni wsAvio;
    private WSZaposleni wsZap;
    private WSAdmin wsAdmin;
    private WSUlogeLicence wsUL;
    private List<Tipaviona> tipovi;

    private KontrolerWS() {
        wsAvio = new WSAvioni();
        wsZap = new WSZaposleni();
    }

    public static KontrolerWS getInstance() {
        if (instance == null) {
            instance = new KontrolerWS();
        }
        return instance;
    }

    public List<Tipaviona> vratiTipove() {
        GenericType<List<Tipaviona>> gtListaTipova = new GenericType<List<Tipaviona>>() {
        };
        tipovi = wsAvio.findAllTypes(gtListaTipova);
        return tipovi;
    }

    public List<Zaposleni> vratiZaposlene() {
        GenericType<List<Zaposleni>> gtListaZaposlenih = new GenericType<List<Zaposleni>>() {
        };
        List<Zaposleni> lz = wsZap.findAll(gtListaZaposlenih);
        return lz;
    }

    public List<Avion> vratiAvione() {
        GenericType<List<Avion>> gtListaAviona = new GenericType<List<Avion>>() {
        };
        List<Avion> la = wsAvio.findAll(gtListaAviona);
        return la;
    }

    public void sacuvajNoviAvion(Avion novi) {
        wsAvio.create(novi);
    }

    public Tipaviona vratiTipPoID(int id) {
        for (Tipaviona t : tipovi) {
            if (t.getTipID() == id) {
                return t;
            }
        }
        return null;
    }

    public void sacuvajIzmenuAviona(Avion a) {
        wsAvio.edit(a, a.getAvionID() + "");
    }

    public void obrisiAvion(Avion a) {
        wsAvio.remove(a.getAvionID() + "");
    }

    public Admin login(Admin a) {
        //ovde puca kod logovanja, ne pozove uopste metodu login webservisa
        a = wsAdmin.login(a);
        return a;
    }

    public String logout(Admin a) {
        String odgovor = wsAdmin.logout(a);
        return odgovor;
    }

    public void obrisiZaposlenog(Zaposleni zap) {
        wsZap.remove(zap.getJmbg());
    }

    public void sacuvajSveZaposlene(List<Zaposleni> dodatiZaposleni) {
        wsZap.createAll(dodatiZaposleni);
    }

    public List<Uloga> vratiListuUlogaZaPilota(Pilot p) {
        GenericType<List<Uloga>> gtListaUloga = new GenericType<List<Uloga>>() {
        };
        List<Uloga> lu = wsUL.vratiUlogeZaPilota(gtListaUloga, p.getJmbg());
        return lu;
    }

    public List<Licenca> vratiListuLicenciZaMehanicara(Aviomehanicar a) {
        GenericType<List<Licenca>> gtListaLicenci = new GenericType<List<Licenca>>() {
        };
        List<Licenca> ll = wsUL.vratiLicenceZaMehanicara(gtListaLicenci, a.getJmbg());
        return ll;
    }

    public String sacuvajIzmenuZaposlenog(Zaposleni novi) {
        return wsZap.edit(novi, novi.getJmbg());
    }

}
