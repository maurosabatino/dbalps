package it.cnr.to.geoclimalp.dbalps.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;
import it.cnr.to.geoclimalp.dbalps.bean.ubicazione.*;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ControllerJson {
	
	/*
	 * get Json
	 */
	public static String getJsonLocazioneAmminitrativa(String path){
		ArrayList<LocazioneAmministrativa> locAmm = new ArrayList<LocazioneAmministrativa>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/LocazioneAmministrativa.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			locAmm = new Gson().fromJson(br, new TypeToken<ArrayList<LocazioneAmministrativa>>(){}.getType());
			sb.append(new Gson().toJson(locAmm).replaceAll("comune", "label"));
			} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getJsonLocazioneIdrologica(String path){
		ArrayList<LocazioneIdrologica> locazioneIdrologica= new ArrayList<LocazioneIdrologica>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/LocazioneIdrologica.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			locazioneIdrologica = new Gson().fromJson(br, new TypeToken<ArrayList<LocazioneIdrologica>>(){}.getType());
			sb.append(new Gson().toJson(locazioneIdrologica).replaceAll("sottobacino", "label"));
			} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	
	
	public static String getJsonProprietaTermiche(String path,String loc){
		ArrayList<ProprietaTermiche> proprietaTermiche = new ArrayList<ProprietaTermiche>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/proprietaTermiche.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			proprietaTermiche = new Gson().fromJson(br, new TypeToken<ArrayList<ProprietaTermiche>>(){}.getType());
			sb.append(new Gson().toJson(proprietaTermiche).replaceAll("proprietaTermiche_"+loc, "label"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static String getJsonStatoFratturazione(String path,String loc){
		ArrayList<StatoFratturazione> statoFratturazione = new ArrayList<StatoFratturazione>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/statoFratturazione.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			statoFratturazione = new Gson().fromJson(br, new TypeToken<ArrayList<StatoFratturazione>>(){}.getType());
			sb.append(new Gson().toJson(statoFratturazione).replaceAll("statoFratturazione_"+loc, "label"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static String getJsonLitologia(String path,String loc){
		ArrayList<Litologia> litologia = new ArrayList<Litologia>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/litologia.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			litologia = new Gson().fromJson(br, new TypeToken<ArrayList<Litologia>>(){}.getType());
			sb.append(new Gson().toJson(litologia).replaceAll("nomeLitologia_"+loc, "label"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static String getJsonSitoProcesso(String path,String loc){
		ArrayList<SitoProcesso> sitoProcesso = new ArrayList<SitoProcesso>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/SitoProcesso.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			sitoProcesso = new Gson().fromJson(br, new TypeToken<ArrayList<SitoProcesso>>(){}.getType());
			sb.append(new Gson().toJson(sitoProcesso).replaceAll("caratteristicaSito_"+loc, "label"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getJsonClasseVolume(String path){
		ArrayList<ClasseVolume> classeVolume = new ArrayList<ClasseVolume>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/classeVolume.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			classeVolume = new Gson().fromJson(br, new TypeToken<ArrayList<ClasseVolume>>(){}.getType());
			sb.append(new Gson().toJson(classeVolume).replaceAll("intervallo", "label"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static String getJsonipologiaProcesso(String path,String loc){
		ArrayList<TipologiaProcesso> tipologiaProcesso = new ArrayList<TipologiaProcesso>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/tipologiaProcesso.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			tipologiaProcesso = new Gson().fromJson(br, new TypeToken<ArrayList<TipologiaProcesso>>(){}.getType());
			sb.append(new Gson().toJson(tipologiaProcesso).replaceAll("nome_"+loc, "label"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/*
	 * create json
	 */
	public static String CreateJsonLocazioneAmministrativa(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<LocazioneAmministrativa> locAmm = ControllerDatabase.prendiLocAmministrativaAll();
		String json = gson.toJson(locAmm);
		try {
			path+="json/LocazioneAmministrativa.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String CreateJsonLocazioneIdrologica(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<LocazioneIdrologica> locazioneIdrologica = ControllerDatabase.prendiLocIdrologicaAll();
		String json = gson.toJson(locazioneIdrologica);
		try {
			path+="json/locazioneIdrologica.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String createJsonEffettiMorfologici(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<EffettiMorfologici> effettiMorfologici = ControllerDatabase.prendiEffettiMOrfologici();
		String json = gson.toJson(effettiMorfologici);
		try {
			path+="json/EffettiMorfologici.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String createJsonDanni(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<Danni> danni = ControllerDatabase.prendiDanni();
		String json = gson.toJson(danni);
		try {
			path+="json/danni.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String createJsonProprietaTermiche(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<ProprietaTermiche> proprietaTermiche = ControllerDatabase.prendiProprietaTermiche();
		String json = gson.toJson(proprietaTermiche);
		try {
			path+="json/proprietaTermiche.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	public static String craeteJsonstatoFratturazione(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<StatoFratturazione> statoFratturazione = ControllerDatabase.prendiStatoFratturazione();
		String json = gson.toJson(statoFratturazione);
		try {
			path+="json/statoFratturazione.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	public static String createJsonLitologia(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<Litologia> litologia = ControllerDatabase.prendiLitologia();
		String json = gson.toJson(litologia);
		try {
			path+="json/litologia.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	public static String CreateJsonSitoProcesso(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<SitoProcesso> SitoProcesso = ControllerDatabase.prendiSitoProcesso();
		String json = gson.toJson(SitoProcesso);
		try {
			path+="json/SitoProcesso.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String CreateJsonClasseVolume(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<ClasseVolume> classeVolume = ControllerDatabase.prendiClasseVolume();
		String json = gson.toJson(classeVolume);
		try {
			path+="json/classeVolume.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String createJsontipologiaProcesso(String path) throws SQLException{
		Gson gson = new Gson();		
		ArrayList<TipologiaProcesso> tipologiaProcesso = ControllerDatabase.prendiTipologiaProcesso();
		String json = gson.toJson(tipologiaProcesso);
		try {
			path+="json/tipologiaProcesso.json";
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	public static String CreateJsonEnte(String path) throws SQLException{
		Gson gson=new Gson();
		ArrayList<Ente> ente=ControllerDatabase.prendiTuttiEnte();
		String json=gson.toJson(ente);
		try{
		path+="json/Ente.json";
		FileWriter writer=new FileWriter(path);
		writer.write(json);
		writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String getJsonEnte(String path){
		ArrayList<Ente> ente = new ArrayList<Ente>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/Ente.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			ente = new Gson().fromJson(br, new TypeToken<ArrayList<Ente>>(){}.getType());
			sb.append(new Gson().toJson(ente).replaceAll("ente", "label"));
			} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String createJsonSitoStazione(String path) throws SQLException{
		Gson gson=new Gson();
		ArrayList<SitoStazioneMetereologica> sito=ControllerDatabase.prendiTuttiSitoStazioneMetereologica();
		String json=gson.toJson(sito);
		try{
		path+="json/SitoStazioneMetereologica.json";
		FileWriter writer=new FileWriter(path);
		writer.write(json);
		writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String getJsonSitoStazione(String path,String loc){
		ArrayList<SitoStazioneMetereologica> sito = new ArrayList<SitoStazioneMetereologica>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/SitoStazioneMetereologica.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			sito = new Gson().fromJson(br, new TypeToken<ArrayList<SitoStazioneMetereologica>>(){}.getType());
			sb.append(new Gson().toJson(sito).replaceAll("caratteristiche_"+loc+"", "label"));
			} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String CreateJsonSensori(String path) throws SQLException{
		Gson gson=new Gson();
		ArrayList<Sensori> sensori=ControllerDatabase.prendiTuttiSensori();
		String json=gson.toJson(sensori);
		try{
		path+="json/Sensori.json";
		FileWriter writer=new FileWriter(path);
		writer.write(json);
		writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String getJsonSensori(String path,String loc){
		ArrayList<Sensori> sensori = new ArrayList<Sensori>();
		StringBuilder sb = new StringBuilder();
		try {
			path+="json/Sensori.json";
			BufferedReader br = new BufferedReader(new FileReader(path));
			sensori = new Gson().fromJson(br, new TypeToken<ArrayList<Sensori>>(){}.getType());
			sb.append(new Gson().toJson(sensori).replaceAll("sensori_"+loc+"", "label"));
			} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void creaJson(String path) throws SQLException{
		System.out.println("creo json");
		String uploadPath = path + "\\" + "json";
		System.out.println("upload path: "+uploadPath);
		File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
        uploadDir.mkdir();
    }
		craeteJsonstatoFratturazione(path);
		createJsonDanni(path);
		createJsonEffettiMorfologici(path);
		createJsonLitologia(path);
		createJsontipologiaProcesso(path);
		CreateJsonClasseVolume(path);
		createJsonProprietaTermiche(path);
		CreateJsonLocazioneAmministrativa(path);
		CreateJsonLocazioneIdrologica(path);
		CreateJsonSitoProcesso(path);
		CreateJsonEnte(path);
		CreateJsonSensori(path);
		createJsonSitoStazione(path);
	}
}
