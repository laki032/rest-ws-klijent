package domain;

import java.io.Serializable;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Uloga implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nazivUloge;
    private Pilot pilot;
    private Avion avion;

    public Uloga() {
    }

    public String getNazivUloge() {
        return nazivUloge;
    }

    public void setNazivUloge(String nazivUloge) {
        this.nazivUloge = nazivUloge;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    @Override
    public String toString() {
        return "uloga[ " + avion.getOznaka() + " ]";
    }

}
