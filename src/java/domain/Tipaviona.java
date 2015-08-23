package domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Tipaviona implements Serializable {

    private int tipID;
    private String naziv;
    private List<Avion> avionList;
    private List<Licenca> licencaList;

    public Tipaviona() {
    }

    public Tipaviona(int tipID) {
        this.tipID = tipID;
    }

    public int getTipID() {
        return tipID;
    }

    public void setTipID(int tipID) {
        this.tipID = tipID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    
    public List<Avion> getAvionList() {
        return avionList;
    }

    public void setAvionList(List<Avion> avionList) {
        this.avionList = avionList;
    }

    public List<Licenca> getLicencaList() {
        return licencaList;
    }

    public void setLicencaList(List<Licenca> licencaList) {
        this.licencaList = licencaList;
    }

    @Override
    public String toString() {
        return naziv;
    }

}
