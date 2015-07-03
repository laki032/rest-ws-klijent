package domain;

import java.io.Serializable;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Zaposleni implements Serializable {
    
    private String jmbg;
    private String imePrezime;
    private int godinaRodjenja;
    private Pilot pilot;
    private Aviomehanicar aviomehanicar;

    public Zaposleni() {
    }

    public Zaposleni(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja;
    }

    public void setGodinaRodjenja(int godinaRodjenja) {
        this.godinaRodjenja = godinaRodjenja;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Aviomehanicar getAviomehanicar() {
        return aviomehanicar;
    }

    public void setAviomehanicar(Aviomehanicar aviomehanicar) {
        this.aviomehanicar = aviomehanicar;
    }

    @Override
    public String toString() {
        return "[" + jmbg + "] " + getImePrezime();
    }
    
}
