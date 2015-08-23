/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.klijent.kontroler;

import domain.Avion;
import domain.Tipaviona;
import domain.Zaposleni;
import java.util.List;
import javax.ws.rs.core.GenericType;
import ws.klijent.WSAvioni;
import ws.klijent.WSZaposleni;

public class KontrolerWS {

    private static KontrolerWS instance;
    private WSAvioni wsAvio;
    private WSZaposleni wsZap;
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
        GenericType<Boolean> odgovor = new GenericType<Boolean>() {
        };
        //ovde puca i zato vraca da nije uspeo da unese avion,
        //problem pravi ovaj Boolean klasa, a dobija boolean kao prost tip za odgovor
        wsAvio.create(novi, odgovor);
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
        GenericType<Boolean> odgovor = new GenericType<Boolean>() {
        };
        wsAvio.edit(a, odgovor, a.getAvionID() + "");
    }

    public void obrisiAvion(Avion a) {
        GenericType<Boolean> odgovor = new GenericType<Boolean>() {
        };
        wsAvio.remove(odgovor, a.getAvionID() + "");
    }

    public void login(String ime, String pass) {
//napravi adminWS i na njemu nekako resi login i out
    }

    public void logout(String ime, String pass) {

    }

    public void obrisiZaposlenog(Zaposleni zap) {
        GenericType<Boolean> odgovor = new GenericType<Boolean>() {
        };
        wsZap.remove(odgovor, zap.getJmbg());
    }

}
