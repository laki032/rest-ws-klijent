package ws.client.controller;

import domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import ws.client.*;

public class KontrolerWS {

    private static KontrolerWS instance;
    private final WSAvioni wsAvio;
    private final WSZaposleni wsZap;
    private final WSAdmin wsAdmin;
    private final WSUlogeLicence wsUL;
    private static final Logger log = Logger.getLogger("KONTROLER: ");
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
        log.log(Level.INFO, "getTypes {0}", tipovi);
        return tipovi;
    }

    public List<Zaposleni> getEmployees() {
        GenericType<List<Zaposleni>> gtListaZaposlenih = new GenericType<List<Zaposleni>>() {
        };
        GenericType<List<Pilot>> gtListaP = new GenericType<List<Pilot>>() {
        };
        GenericType<List<Aviomehanicar>> gtListaA = new GenericType<List<Aviomehanicar>>() {
        };
        List<Zaposleni> lz = wsZap.findAll(gtListaZaposlenih);
        List<Pilot> lp = wsZap.findAllPilot(gtListaP);
        List<Aviomehanicar> la = wsZap.findAllAvioMehanicar(gtListaA);
        log.log(Level.INFO, "getEmployees {0}", lz);
        log.log(Level.INFO, "getAllPilot {0}", lp);
        log.log(Level.INFO, "getAllMehanicar {0}", la);

        List<Zaposleni> employees = new ArrayList<>();

        for (Pilot p : lp) {
            for (Zaposleni z : lz) {
                if (z.getJmbg().equals(p.getJmbg())) {
                    p.setImePrezime(z.getImePrezime());
                    p.setGodinaRodjenja(z.getGodinaRodjenja());
                    employees.add(p);
                    break;
                }
            }
        }
        for (Aviomehanicar a : la) {
            for (Zaposleni z : lz) {
                if (z.getJmbg().equals(a.getJmbg())) {
                    a.setImePrezime(z.getImePrezime());
                    a.setGodinaRodjenja(z.getGodinaRodjenja());
                    employees.add(a);
                    break;
                }
            }
        }
        return employees;
    }

    public List<Avion> getPlanes() {
        GenericType<List<Avion>> gtListaAviona = new GenericType<List<Avion>>() {
        };
        List<Avion> la = wsAvio.findAll(gtListaAviona);
        log.log(Level.INFO, "getPlanes {0}", la);
        return la;
    }

    public void create(Avion plane) {
        log.log(Level.INFO, "create {0}", plane);
        wsAvio.create(plane);
    }

    public Tipaviona getTypeById(int id) {
        log.log(Level.INFO, "getTypeById {0}", id);
        for (Tipaviona t : tipovi) {
            if (t.getTipID() == id) {
                return t;
            }
        }
        return null;
    }

    public void edit(Avion a) {
        log.log(Level.INFO, "edit {0}", a);
        wsAvio.edit(a, a.getAvionID() + "");
    }

    public void remove(Avion a) {
        log.log(Level.INFO, "remove {0}", a);
        wsAvio.remove(a.getAvionID() + "");
    }

    public Admin login(Admin a) throws Exception {
        log.log(Level.INFO, "login {0}", a);
        a = wsAdmin.login(a);
        if (a == null) {
            log.log(Level.INFO, "login failed {0}", a);
            throw new Exception(Constants.ADMIN_LOGIN_FAILURE);
        }
        return a;
    }

    public String logout(Admin a) throws Exception {
        log.log(Level.INFO, "logout {0}", a);
        String odgovor = wsAdmin.logout(a);
        if (odgovor.equals(Constants.ADMIN_LOGOUT_FAILURE)) {
            log.log(Level.INFO, "logout failed {0}", a);
            throw new Exception(odgovor);
        }
        return odgovor;
    }

    public void remove(Zaposleni zap) {
        log.log(Level.INFO, "remove {0}", zap);
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
        if (odg.equals(Constants.EMPLOYEES_CREATE_FAILURE)) {
            throw new Exception(odg);
        }
        log.log(Level.INFO, "saveAll list: {0}", dodatiZaposleni);
        log.log(Level.INFO, "saveAll {0}", odg);
        return odg;
    }

    public List<Uloga> vratiListuUlogaZaPilota(Pilot p) {
        GenericType<List<Uloga>> gtListaUloga = new GenericType<List<Uloga>>() {
        };
        List<Uloga> lu = wsUL.vratiUlogeZaPilota(gtListaUloga, p.getJmbg());
        log.log(Level.INFO, "vratiListuUlogaZaPilota {0}/{1}", new Object[]{p, lu});
        return lu;
    }

    public List<Licenca> vratiListuLicenciZaMehanicara(Aviomehanicar a) {
        GenericType<List<Licenca>> gtListaLicenci = new GenericType<List<Licenca>>() {
        };
        List<Licenca> ll = wsUL.vratiLicenceZaMehanicara(gtListaLicenci, a.getJmbg());
        log.log(Level.INFO, "vratiListuLicenciZaMehanicara {0}/{1}", new Object[]{a, ll});
        return ll;
    }

    public String edit(Zaposleni novi) {
        log.log(Level.INFO, "edit {0}", novi);
        return wsZap.edit(novi, novi.getJmbg());
    }

    public String novaUloga(Uloga novaUloga) {
        log.log(Level.INFO, "novaUloga {0}", novaUloga);
        return wsUL.novaUloga(novaUloga);
    }

    public String novaLicenca(Licenca novaLicenca) {
        log.log(Level.INFO, "novaLicenca {0}", novaLicenca);
        return wsUL.novaLicenca(novaLicenca);
    }

    public Avion getById(int id) {
        log.log(Level.INFO, "getById {0}", id);
        for (Avion a : avioni) {
            if (a.getAvionID() == id) {
                return a;
            }
        }
        return null;
    }

}
