package domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Aviomehanicar extends Zaposleni implements Serializable {

    private String tipMehanicara;
    private List<Licenca> licencaList;

    public Aviomehanicar() {
    }

    public Aviomehanicar(String jmbg) {
        super(jmbg);
    }

    public Aviomehanicar(Zaposleni z) {
        jmbg = z.getJmbg();
        imePrezime = z.getImePrezime();
        godinaRodjenja = z.getGodinaRodjenja();
    }
    
    @Override
    public String getJmbg() {
        return jmbg;
    }

    @Override
    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getTipMehanicara() {
        return tipMehanicara;
    }

    public void setTipMehanicara(String tipMehanicara) {
        this.tipMehanicara = tipMehanicara;
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
