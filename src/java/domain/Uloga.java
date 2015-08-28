package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Uloga implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nazivUloge;
    private Pilot pilot;
    private Avion avion;
    private Date datum;

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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Datuma: " + sdf.format(datum) + ", pilot: " + pilot.getImePrezime() 
                + ", u avionu: " + avion.getOznaka() + ", imao je ulogu: " + nazivUloge;
    }

}
