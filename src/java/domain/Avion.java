package domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Avion implements Serializable {

    private int avionID;
    private String oznaka;
    private int godProizvodnje;
    private int brojPutnika;
    private int nosivost;
    private Tipaviona tipID;
    private List<Uloga> ulogaList;

    public Avion() {
        oznaka = "N/A";
        avionID = 0;
        godProizvodnje = 1990;
        brojPutnika = 0;
        nosivost = 1;
    }

    public Avion(int avionID) {
        this.avionID = avionID;
    }

    public int getAvionID() {
        return avionID;
    }

    public void setAvionID(int avionID) {
        this.avionID = avionID;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public int getGodProizvodnje() {
        return godProizvodnje;
    }

    public void setGodProizvodnje(int godProizvodnje) {
        this.godProizvodnje = godProizvodnje;
    }

    public int getBrojPutnika() {
        return brojPutnika;
    }

    public void setBrojPutnika(int brojPutnika) {
        this.brojPutnika = brojPutnika;
    }

    public int getNosivost() {
        return nosivost;
    }

    public void setNosivost(int nosivost) {
        this.nosivost = nosivost;
    }

    public Tipaviona getTipID() {
        return tipID;
    }

    public void setTipID(Tipaviona tipID) {
        this.tipID = tipID;
    }
    
    public List<Uloga> getUlogaList() {
        return ulogaList;
    }

    public void setUlogaList(List<Uloga> ulogaList) {
        this.ulogaList = ulogaList;
    }

    @Override
    public String toString() {
        return "[" + avionID + "] " + oznaka;
    }

}
