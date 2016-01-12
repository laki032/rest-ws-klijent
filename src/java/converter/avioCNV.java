package converter;

import domain.Avion;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import ws.client.controller.KontrolerWS;

/**
 *
 * @author Lazar Vujadinovic
 */
@FacesConverter(value = "avioCNV")
public class avioCNV implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            int id = (int) Long.parseLong(value);
            Avion a = KontrolerWS.getInstance().getById(id);
            System.out.println("tip konverter:" + a.getOznaka());
            return a;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && (value instanceof Avion)) {
            Avion a = (Avion) value;
            return a.getAvionID()+ "";
        }
        return "";
    }

}
