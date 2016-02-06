package mb;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author laki
 */
@ManagedBean
@ApplicationScoped
public class MbTranslator {

    public enum Language {
        EN, SR
    }
    private static Language language;

    private static Map<String, String> sr_translations;
    private static Map<String, String> en_translations;

    private static final Logger log = Logger.getLogger(MbTranslator.class.getName());
    private static final String pathSR = "../../../web/resources/translations/sr.properties";
    private static final String pathEN = "../../../web/resources/translations/en.properties";

    public MbTranslator() {
    }

    static {
        sr_translations = new HashMap<>();
        en_translations = new HashMap<>();
        Properties sr = new Properties();
        Properties en = new Properties();
        try {
            InputStream inSR = MbTranslator.class.getClassLoader().getResourceAsStream(pathSR);
            InputStream inEN = MbTranslator.class.getClassLoader().getResourceAsStream(pathEN);
            sr.load(inSR);
            en.load(inEN);
            inSR.close();
            inEN.close();
            log.log(Level.INFO, "Translations loaded!");
        } catch (Exception e) {
            log.log(Level.WARNING, "ERROR loading translations! {0}", e.getMessage());
        }

        for (String key : sr.stringPropertyNames()) {
            String value = sr.getProperty(key);
            sr_translations.put(key, value);
        }

        for (String key : en.stringPropertyNames()) {
            String value = en.getProperty(key);
            en_translations.put(key, value);
        }
        readLanguage();
    }

    /**
     * reads language from cookie, if there is no one, sets SR as default
     */
    private static void readLanguage() {
        Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        Cookie c = (Cookie) cookies.get("lang");
        String lang = c != null ? (String) c.getValue() : "SR";
        switch (lang) {
            case "EN":
                setLanguage(Language.EN);
                break;
            case "SR":
            default:
                setLanguage(Language.SR);
        }
    }

    private static Object setLanguage(Language l) {
        language = l;
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        Cookie c = new Cookie("lang", l.toString());
        c.setMaxAge(Integer.MAX_VALUE);
        c.setPath("/");
        response.addCookie(c);
        log.log(Level.INFO, "Language changed to: {0}", l.toString());
        return null;
    }

    public void translateToEN() {
        setLanguage(Language.EN);
    }

    public void translateToSR() {
        setLanguage(Language.SR);
    }

    public static String translate(String s) {
        if (s == null) {
            return "";
        }
        switch (language) {
            case EN:
                return en_translations.get(s) != null ? en_translations.get(s) : s;
            case SR:
            default:
                return sr_translations.get(s) != null ? sr_translations.get(s) : s;
        }

    }
}
