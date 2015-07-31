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
        List<Tipaviona> lt = wsAvio.findAllTypes(gtListaTipova);
        return lt;
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
        wsAvio.create(novi, null);
    }


}
