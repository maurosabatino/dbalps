package it.cnr.to.geoclimalp.dbalps.controller;

import java.util.Locale;
import java.util.MissingResourceException;
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

        String word = "empty";
        try {
            if (!(keyword.equals(" "))) {
                word = translation.getString(keyword);
                return word;
            }

        } finally {
            return word;
        }

    }

    public String getLanguage() {

        try {
            return translation.getLocale().getLanguage();
        } catch (MissingResourceException mre) {
            System.err.println("no language");
        }
        return null;
    }

}
