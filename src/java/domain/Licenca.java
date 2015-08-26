package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lazar Vujadinovic
 */
public class Licenca implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date datumDobijanja;
    private Aviomehanicar aviomehanicar;
    private Tipaviona tipaviona;

    public Licenca() {
    }

    public String getJmbg() {
        return aviomehanicar.getJmbg();
    }

    public void setJmbg(String jmbg) {
        this.aviomehanicar.setJmbg(jmbg);
    }

    public int getTipID() {
        return tipaviona.getTipID();
    }

    public void setTipID(int tipID) {
        this.tipaviona.setTipID(tipID);
    }

    public Date getDatumDobijanja() {
        return datumDobijanja;
    }

    public void setDatumDobijanja(Date datumDobijanja) {
        this.datumDobijanja = datumDobijanja;
    }

    public Aviomehanicar getAviomehanicar() {
        return aviomehanicar;
    }

    public void setAviomehanicar(Aviomehanicar aviomehanicar) {
        this.aviomehanicar = aviomehanicar;
    }

    public Tipaviona getTipaviona() {
        return tipaviona;
    }

    public void setTipaviona(Tipaviona tipaviona) {
        this.tipaviona = tipaviona;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Datuma: " + sdf.format(datumDobijanja) + ", mehanicar: " + aviomehanicar.getImePrezime() 
                + ", \ndobio je licencu za " + tipaviona.getNaziv()+ " tip aviona";
    }
    
}
