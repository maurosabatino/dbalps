package it.cnr.to.geoclimalp.dbalps.controller;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Mauro
 */
public class ControllerLingua {

    private ResourceBundle translation;

    public ControllerLingua() {
        translation = ResourceBundle.getBundle("it.cnr.to.geoclimalp.dbalps.resources.string", Locale.forLanguageTag("en-US"));
    }

    public ControllerLingua(Locale locale) {
        translation = ResourceBundle.getBundle("it.cnr.to.geoclimalp.dbalps.resources.string", locale);
    }

    public String getWord(String keyword) {
        return translation.getString(keyword);
    }

    public String getLanguage() {
        String word = "";
        try {
            word = translation.getLocale().getLanguage();
        } catch (Error mre) {
            System.err.println("string not found");
        }
        return word;
    }

    public static void main(String[] args) {
        ControllerLingua cl = new ControllerLingua((Locale.forLanguageTag("it-IT")));
        System.out.println("ita: " + cl.getLanguage());
        cl = new ControllerLingua((Locale.forLanguageTag("en-US")));
        System.out.println("eng: " + cl.getLanguage());
    }

}
