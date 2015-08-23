package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Pilot extends Zaposleni implements Serializable {

    private String jmbg;
    private boolean ocenaStanja;
    private Date datumPregleda;
    private List<Uloga> ulogaList;
    private Zaposleni zaposleni;

    public Pilot() {
    }

    public Pilot(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public boolean getOcenaStanja() {
        return ocenaStanja;
    }

    public void setOcenaStanja(boolean ocenaStanja) {
        this.ocenaStanja = ocenaStanja;
    }

    public Date getDatumPregleda() {
        return datumPregleda;
    }

    public void setDatumPregleda(Date datumPregleda) {
        this.datumPregleda = datumPregleda;
    }

    public List<Uloga> getUlogaList() {
        return ulogaList;
    }

    public void setUlogaList(List<Uloga> ulogaList) {
        this.ulogaList = ulogaList;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    @Override
    public String toString() {
        return "pilot[" + jmbg + "] " + getImePrezime();
    }

}
