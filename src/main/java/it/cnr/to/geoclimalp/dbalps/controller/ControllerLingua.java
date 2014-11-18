
package it.cnr.to.geoclimalp.dbalps.controller;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Mauro
 */
public class ControllerLingua {
  private  ResourceBundle translation;
    public ControllerLingua(Locale locale){
      translation = ResourceBundle.getBundle("it.cnr.to.geoclimalp.dbalps.resources.string",locale);
    }
  
    public String getWord(String keyword){
        return translation.getString(keyword);
    }
    public String getLanguage(){
      return translation.getLocale().getLanguage();
    }
    
    public static void main(String[] args){
      ControllerLingua cl = new ControllerLingua((Locale.forLanguageTag("it-IT")));
      System.out.println("ita: "+cl.getLanguage());
      cl = new ControllerLingua((Locale.forLanguageTag("en-US")));
      System.out.println("eng: "+cl.getLanguage());
    }
   
}
