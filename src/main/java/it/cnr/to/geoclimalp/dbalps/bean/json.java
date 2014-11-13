/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.to.geoclimalp.dbalps.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.LocazioneAmministrativa;
import it.cnr.to.geoclimalp.dbalps.controller.ControllerDatabase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Mauro
 */
@Named(value = "json")
@Dependent
public class json {

    private String locazioneAmministrativa;
    /**
     * Creates a new instance of json
     */
    public json() {
        locazioneAmministrativa="";
    }

    /**
     * @return the locazioneAmministrativa
     */
    public String getLocazioneAmministrativa() {
        String path="C:\\Users\\Mauro\\AppData\\Roaming\\NetBeans\\8.0.1\\config\\GF_4.1\\domain1\\resources\\json\\LocazioneAmministrativa.json";
        System.out.println(path);
        ArrayList<LocazioneAmministrativa> locAmm = new ArrayList<LocazioneAmministrativa>();
		StringBuilder sb = new StringBuilder();
		try {		
			BufferedReader br = new BufferedReader(new FileReader(path));
			locAmm = new Gson().fromJson(br, new TypeToken<ArrayList<LocazioneAmministrativa>>(){}.getType());
			sb.append(new Gson().toJson(locAmm).replaceAll("comune", "label"));
			} catch (IOException e) {
			e.printStackTrace();
		}
		locazioneAmministrativa = sb.toString();
        return locazioneAmministrativa;
    }

    /**
     * @param locazioneAmministrativa the locazioneAmministrativa to set
     */
    public void setLocazioneAmministrativa(String locazioneAmministrativa) throws SQLException {
        Gson gson = new Gson();	
        String path="C:\\Users\\Mauro\\AppData\\Roaming\\NetBeans\\8.0.1\\config\\GF_4.1\\domain1\\resources\\json";
		ArrayList<LocazioneAmministrativa> locAmm = ControllerDatabase.prendiLocAmministrativaAll();
		this.locazioneAmministrativa= gson.toJson(locAmm);
		try {
			path+="json/LocazioneAmministrativa.json";
			FileWriter writer = new FileWriter(path);
			writer.write(this.locazioneAmministrativa);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        
    }
    
    
}
