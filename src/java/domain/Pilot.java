package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Pilot extends Zaposleni implements Serializable {

    private boolean ocenaStanja;
    private Date datumPregleda;
    private List<Uloga> ulogaList;

    public Pilot() {
        super();
        datumPregleda = new Date();
    }

    public Pilot(String jmbg) {
        super(jmbg);
        datumPregleda = new Date();
    }
    
    public Pilot(Zaposleni z) {
        jmbg = z.getJmbg();
        imePrezime = z.getImePrezime();
        godinaRodjenja = z.getGodinaRodjenja();
        datumPregleda = new Date();
    }

    @Override
    public String getJmbg() {
        return jmbg;
    }

    @Override
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

    @Override
    public String toString() {
        return "pilot[" + jmbg + "] " + getImePrezime();
    }
}
