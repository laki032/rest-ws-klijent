package converter;

import domain.Tipaviona;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import ws.client.controller.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@FacesConverter(value = "tipCNV")
public class tipCNV implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            int id = (int) Long.parseLong(value);
            Tipaviona t = KontrolerWS.getInstance().vratiTipPoID(id);
            System.out.println("tip konverter:" + t.getNaziv());
            return t;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && (value instanceof Tipaviona)) {
            Tipaviona t = (Tipaviona) value;
            return t.getTipID() + "";
        }
        return "";
    }

}
