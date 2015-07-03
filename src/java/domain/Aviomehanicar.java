package domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Aviomehanicar extends Zaposleni implements Serializable {

    private static final long serialVersionUID = 1L;
    private String jmbg;
    private String tipMehanicara;
    private Zaposleni zaposleni;
    private List<Licenca> licencaList;

    public Aviomehanicar() {
    }

    public Aviomehanicar(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getTipMehanicara() {
        return tipMehanicara;
    }

    public void setTipMehanicara(String tipMehanicara) {
        this.tipMehanicara = tipMehanicara;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public List<Licenca> getLicencaList() {
        return licencaList;
    }

    public void setLicencaList(List<Licenca> licencaList) {
        this.licencaList = licencaList;
    }

    @Override
    public String toString() {
        return "mehanicar[" + jmbg + "] " + getImePrezime();
    }

}
