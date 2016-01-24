package mb;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

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
    private Language language;

    private Map<String, String> sr_translations;
    private Map<String, String> en_translations;
    
    private static final Logger log = Logger.getLogger(MbTranslator.class.getName());
    private final String pathSR = "../../../web/resources/translations/sr.properties";
    private final String pathEN = "../../../web/resources/translations/en.properties";
    
    public MbTranslator() {
    }

    @PostConstruct
    public void init() {
        language = Language.SR;
        sr_translations = new HashMap<>();
        en_translations = new HashMap<>();
        Properties sr = new Properties();
        Properties en = new Properties();
        try {
            InputStream inSR = getClass().getClassLoader().getResourceAsStream(pathSR);
            InputStream inEN = getClass().getClassLoader().getResourceAsStream(pathEN);
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
    }

    private Object setLanguage(Language l) {
        language = l;
        log.log(Level.INFO, "Language changed to: {0}", l.toString());
        return null;
    }

    public void translateToEN() {
        setLanguage(Language.EN);
    }

    public void translateToSR() {
        setLanguage(Language.SR);
    }

    public String translate(String s) {
        switch (language) {
            case EN:
                return en_translations.get(s) != null ? en_translations.get(s) : s;
            case SR:
            default:
                return sr_translations.get(s) != null ? sr_translations.get(s) : s;
        }

    }
}
