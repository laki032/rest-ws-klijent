/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.client.controller;

import domain.*;
import java.util.List;
import javax.ws.rs.core.GenericType;
import ws.client.*;

public class KontrolerWS {

    private static KontrolerWS instance;
    private WSAvioni wsAvio;
    private WSZaposleni wsZap;
    private WSAdmin wsAdmin;
    private WSUlogeLicence wsUL;
    private List<Tipaviona> tipovi;
    private List<Avion> avioni;

    private KontrolerWS() {
        wsAvio = new WSAvioni();
        wsZap = new WSZaposleni();
        wsAdmin = new WSAdmin();
        wsUL = new WSUlogeLicence();
    }

    public static KontrolerWS getInstance() {
        if (instance == null) {
            instance = new KontrolerWS();
        }
        return instance;
    }

    public List<Tipaviona> getTypes() {
        GenericType<List<Tipaviona>> gtListaTipova = new GenericType<List<Tipaviona>>() {
        };
        GenericType<List<Avion>> gtListaAviona = new GenericType<List<Avion>>() {
        };
        tipovi = wsAvio.findAllTypes(gtListaTipova);
        avioni = wsAvio.findAll(gtListaAviona);
        return tipovi;
    }

    public List<Zaposleni> getEmployees() {
        GenericType<List<Zaposleni>> gtListaZaposlenih = new GenericType<List<Zaposleni>>() {
        };
        List<Zaposleni> lz = wsZap.findAll(gtListaZaposlenih);
        return lz;
    }

    public List<Avion> getPlanes() {
        GenericType<List<Avion>> gtListaAviona = new GenericType<List<Avion>>() {
        };
        List<Avion> la = wsAvio.findAll(gtListaAviona);
        return la;
    }

    public void create(Avion plane) {
        wsAvio.create(plane);
    }

    public Tipaviona getTypeById(int id) {
        for (Tipaviona t : tipovi) {
            if (t.getTipID() == id) {
                return t;
            }
        }
        return null;
    }

    public void edit(Avion a) {
        wsAvio.edit(a, a.getAvionID() + "");
    }

    public void remove(Avion a) {
        wsAvio.remove(a.getAvionID() + "");
    }

    public Admin login(Admin a) throws Exception {
        a = wsAdmin.login(a);
        if (a == null) {
            throw new Exception("admin nije ulogovan");
        }
        return a;
    }

    public String logout(Admin a) throws Exception {
        String odgovor = wsAdmin.logout(a);
        if (odgovor.startsWith("nije")) {
            throw new Exception(odgovor);
        }
        return odgovor;
    }

    public void remove(Zaposleni zap) {
        wsZap.remove(zap.getJmbg());
    }

    public String saveAll(List<Zaposleni> dodatiZaposleni) throws Exception {
        Zaposleni[] lz = new Zaposleni[dodatiZaposleni.size()];
        for (int i = 0; i < lz.length; i++) {
            Zaposleni z = dodatiZaposleni.get(i);
            Zaposleni novi = new Zaposleni(z.getJmbg());
            novi.setGodinaRodjenja(z.getGodinaRodjenja());
            novi.setImePrezime(z.getImePrezime());
            if (z instanceof Pilot) {
                novi.setPilot(new Pilot(z.getJmbg()));
                novi.getPilot().setDatumPregleda(((Pilot) z).getDatumPregleda());
                novi.getPilot().setImePrezime(z.getImePrezime());
                novi.getPilot().setGodinaRodjenja(z.getGodinaRodjenja());
                novi.getPilot().setOcenaStanja(((Pilot) z).getOcenaStanja());
            } else {
                novi.setAviomehanicar(new Aviomehanicar(z.getJmbg()));
                novi.getAviomehanicar().setTipMehanicara(((Aviomehanicar) z).getTipMehanicara());
                novi.getAviomehanicar().setImePrezime(z.getImePrezime());
                novi.getAviomehanicar().setGodinaRodjenja(z.getGodinaRodjenja());
            }
            lz[i] = novi;
        }
        String odg = wsZap.createAll(lz);
        if (odg.startsWith("cuvanje")) {
            throw new Exception(odg);
        }
        return odg;
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

    public String edit(Zaposleni novi) {
        return wsZap.edit(novi, novi.getJmbg());
    }

    public String novaUloga(Uloga novaUloga) {
        return wsUL.novaUloga(novaUloga);
    }

    public String novaLicenca(Licenca novaLicenca) {
        return wsUL.novaLicenca(novaLicenca);
    }

    public Avion getById(int id) {
        for (Avion a : avioni) {
            if (a.getAvionID() == id) {
                return a;
            }
        }
        return null;
    }

}
