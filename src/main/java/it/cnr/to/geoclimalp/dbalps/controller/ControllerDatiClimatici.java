package it.cnr.to.geoclimalp.dbalps.controller;


import it.cnr.to.geoclimalp.dbalps.html.HTMLElaborazioni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import it.cnr.to.geoclimalp.dbalps.bean.Dati;
import it.cnr.to.geoclimalp.dbalps.bean.Grafici;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;


public class ControllerDatiClimatici {
	static String url = "jdbc:postgresql://localhost:5432/dbalps";
	static String user = "postgres";
	static String pwd = "guido2014";
	
	/*           GENERICI         */
	public static double arrotonda(double n){
			double d2 = (int)(n*10000);	
			d2 /= 100;	
			d2=Math.round(d2);
			 d2 /= 100;
			 
			return d2;
		}
	
	public static double interpolazione(ArrayList<Double> temperature, ArrayList<Double> prob, double riferimento){
		double risultato=0;
		int t=0;
		int p=0;
		System.out.println(		"  temperatura="+temperature.size());
		for(t=0;t<temperature.size()-1;t++){
			if(temperature.get(t).compareTo(riferimento)==0) return prob.get(p);
			else if(temperature.get(t).compareTo(riferimento)<0 && 0<temperature.get(t+1).compareTo(riferimento)){
					risultato=((riferimento-temperature.get(t+1))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p)-((riferimento-temperature.get(t))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p+1);
					return risultato;
			}
			if(t==temperature.size()-1 || temperature.get(t).compareTo(temperature.get(t+1))!=0) {
				p++;
			}		
		}
		System.out.println("riferimento="+riferimento+"" );
		System.out.println(		" ultima temperatura="+temperature.get(temperature.size()-1));
		if(riferimento>temperature.get(temperature.size()-1)) return 1;
		else return -1;
	}
	public static int dataLimite(Timestamp d,int limite){
		int data = 0;
		Calendar cal=new GregorianCalendar();
		cal.setTime(d);
		cal.add(Calendar.DAY_OF_YEAR, limite);
		cal.add(Calendar.MONTH, 1);
		if(cal.get(Calendar.DAY_OF_MONTH)<10)
			data=Integer.parseInt(""+cal.get(Calendar.MONTH)+"0"+cal.get(Calendar.DAY_OF_MONTH));
		else
			data=Integer.parseInt(""+cal.get(Calendar.MONTH)+""+cal.get(Calendar.DAY_OF_MONTH));
		return data;
	}
	
	public static int annoRiferimento(Timestamp t, int id) throws SQLException{
		int anno=0;
		Calendar cal=new GregorianCalendar();
		cal.setTime(t);
		anno=cal.get(Calendar.YEAR);
		return anno;
	}
	
	 	
	
	
	public static void incrementoTempo(Timestamp t){
		Calendar cal=new GregorianCalendar();
		cal.setTime(t);
		cal.add(Calendar.MONTH, 1);
		for(int i=0;i<10;i++){
					cal.add(Calendar.DAY_OF_YEAR,1);
					System.out.println(""+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH));

		}
	}
	
	public static int controlloAnni(String tabella,int idstazione) throws SQLException{
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		int anni=0;
		ResultSet rs =st.executeQuery("select count(distinct(EXTRACT(year FROM data))) as anni from "+tabella+" where idstazionemetereologica="+idstazione+"");
		while(rs.next()){
			anni=(rs.getInt("anni"));
		}		
		rs.close();
		st.close();
		return anni;
	}
	
	public static boolean controlloDati(int dati,int aggregazione,String tabella,int idstazione) throws SQLException{
		boolean ok=false;
		if(dati!=0){
			int anni=controlloAnni(tabella,idstazione);
			if((aggregazione*2+1)*anni==dati) ok=true; 
		}
		
		return ok;
	}
	
	public static ArrayList<Double> distribuzioneFrequenzaCumulativa(ArrayList<Double> dati){//array gia ordinato dal db
		ArrayList<Double> temp = new ArrayList<Double>();
		double probabilita=0;
		int cont=0;		
		for(int i=0;i<dati.size();i++){
			if(i!=dati.size()-1){
			 if((dati.get(i).equals(dati.get(i+1)))==false){
				cont++;
				probabilita=(double)(i+1)/(double)(dati.size()+1);
				temp.add(probabilita);
			}			
		}else{
			probabilita=(double)(i+1)/(double)(dati.size()+1);
			temp.add(probabilita);
		}		
	}
	    return temp;
	}
	
	
	/*            ELABORAZIONI      */
	
	public static ArrayList<Double> mediaMobilePrecipitazioni(ArrayList<Double> dati, int finestra,int aggregazione,int annoriferimento){
		// finestra
		// aggregazione giorni tot
		ArrayList<Double> prec = new ArrayList<Double>();
		int inf=0;
		int anno=1;
		int sup=aggregazione;
		int riferimento=aggregazione/2+1;
		double precrif=0;
		double somma=0;
		 while(inf<(dati.size()-finestra)){
			while(inf<(sup-finestra+1)){			  
			 	for(int i=inf;i<inf+finestra;i++){
				 	if(dati.get(i)==-9999) dati.set(i,0.0); 
				 	somma = somma+dati.get(i);
				 	somma=arrotonda(somma);
			 		}
			 		if(somma!=0){ 
			 			if(anno>20) System.out.println("inf="+inf+" conf="+(aggregazione*(annoriferimento-1)+riferimento-finestra)+" anno="+anno);			
			 			if(inf==(aggregazione*(annoriferimento-1)+riferimento-finestra) && anno==annoriferimento){
			 				System.out.println("anno riferimento");
			 				precrif=somma;
			 				precrif=arrotonda(precrif);
			 			}
			 			else prec.add(somma);  
				}
				somma=0;
				inf++;
				}
			inf=sup;
			anno++;
			sup=(aggregazione)*anno;
		}
		 
		Collections.sort(prec);
		prec.add(precrif);
		return prec;
	}

	
	
	public static ArrayList<Double> mediaMobileDeltaT(ArrayList<Double> dati, int finestra,int aggregazione,int annoriferimento){
																											// finestra
																											// aggregazione giorni tot
		ArrayList<Double> a = new ArrayList<Double>();
		int inf=0;
		double deltaT=0;
		int anno=1;
		int sup=aggregazione;
		int riferimento=aggregazione/2+1;
		double deltarif=0;
		while(inf<(dati.size()-finestra)){
			while(inf<(sup-finestra+1)){
				if(inf==(aggregazione*(annoriferimento-1)+riferimento-finestra) && anno==annoriferimento){
					deltarif=dati.get(inf+finestra-1)-dati.get(inf);
					deltarif=arrotonda(deltarif);
				}else {
					if(dati.get(inf)!=-9999 && dati.get(inf+finestra-1)!=-9999){
						 deltaT=dati.get(inf+finestra-1)-dati.get(inf);
						 deltaT=arrotonda(deltaT);
						 a.add(deltaT);
					 }
				}
				inf++;
			}
			inf=sup;
			anno++;
			sup=(aggregazione)*anno;
		}
	
		Collections.sort(a);
		a.add(deltarif);
		return a;
	}
	

	
	
	public static ArrayList<Double> prendiTDelta(Timestamp t,int limite, int id) throws SQLException{// limite = intervallo a dx/sx es 15 su aggregazione 30 giorni
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
		int limiteinf=dataLimite(t,-limite);
		int limitesup=dataLimite(t,limite);
		ResultSet rs =st.executeQuery("SELECT temperaturaavg,EXTRACT(year FROM data) FROM temperatura_avg WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");
		int anno=0;
		while(rs.next()){
			tem.add(rs.getDouble("temperaturaavg"));
		}
		rs.close();
		st.close();
		return tem;
	
	}
	
	public static ArrayList<Double> prendiPrecipitazioni(Timestamp t,int limite,int id) throws SQLException{// limite = intervallo a dx/sx es 15 su aggregazione 30 giorni
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> prec= new ArrayList<Double>();
		int limiteinf=dataLimite(t,-limite);
		int limitesup=dataLimite(t,limite);
		ResultSet rs =st.executeQuery("SELECT quantita FROM precipitazione WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");
		
		System.out.println("SELECT quantita FROM precipitazione WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");
		
		while(rs.next()){
			prec.add(rs.getDouble("quantita"));
		}
		rs.close();
		st.close();
		return prec;
	
	}
	
	
	public static ArrayList<Double> prendiT(Timestamp d,int id,int limite,String tipo) throws SQLException{
		double riferimento=0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
	//	ArrayList<Double> media= new ArrayList<Double>();
		Calendar cal=new GregorianCalendar();
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1);
		int limiteinf=dataLimite(d,-limite);
		int limitesup=dataLimite(d,0);
		ResultSet rs;
		double cont=0;
		double med=0;
		boolean rif=false;
		boolean nullo=false;
		rs =st.executeQuery("SELECT temperatura"+tipo+",data FROM temperatura_"+tipo+" WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");
		while(rs.next()){
			if(cont<limite+1){	
				if(rs.getDouble("temperatura"+tipo+"")!=-9999 && rs.getTimestamp("data").equals(d) ){
					//riferimento=rs.getDouble("temperaturaavg");
					rif=true;
					med=med+rs.getDouble("temperatura"+tipo+"");
					nullo=true;
				}
				else if(rs.getDouble("temperatura"+tipo+"")!=-9999) {
					
					//tem.add(rs.getDouble("temperaturaavg"));
					med=med+rs.getDouble("temperatura"+tipo+"");
					nullo=true;
				}
				cont++;
				if(cont==limite+1 ){
					if(rif==true){
						riferimento=med/(limite+1);
						rif=false;
					}
					else{ 
						if(nullo==true) tem.add(med/(limite+1));
						med=0;
					}
					cont=0;
					nullo=false;
				}
			}
			
		}
		
		Collections.sort(tem);
		tem.add(riferimento);
		rs.close();
		st.close();
		return tem;
	}
	
	
	
	public static ArrayList<Grafici> deltaT(ArrayList<StazioneMetereologica> stazione,int finestra,int aggregazione,Timestamp t,ControllerLingua locale) throws SQLException{
		String sb = "";
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		
		//cambiare inserimenti metodi controller dati climatici
		double deltarif=0;
		double interpolazione=0;
		boolean ok=true;
		for(int i=0;i<stazione.size();i++){
			StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica(stazione.get(i).getIdStazioneMetereologica(),locale);
			int anno=ControllerDatiClimatici.annoRiferimento(t, s.getIdStazioneMetereologica());
			Grafici gra=new Grafici();
			ArrayList<Double> temperature = ControllerDatiClimatici.prendiTDelta(t, aggregazione,s.getIdStazioneMetereologica());
			//ok=ControllerDatiClimatici.controlloDati(temperature.size(),aggregazione,"temperatura_avg",s.getIdStazioneMetereologica());
			String nome=s.getNome();

			if(ok==true){
			
				ArrayList<Double> deltaT=ControllerDatiClimatici.mediaMobileDeltaT(temperature,finestra,aggregazione*2+1,21);
				deltarif=deltaT.get(deltaT.size()-1);
				deltaT.remove(deltaT.size()-1);
				ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(deltaT);
				interpolazione=ControllerDatiClimatici.interpolazione(deltaT, probabilita,deltarif);
				gra.setInterpolazione(interpolazione);
				gra.setNome(nome);
				gra.setRiferimento(deltarif);
				gra.setX(deltaT);
				gra.setY(probabilita);
				gra.setOk(ok);
				g.add(gra);
			}
			else{
				gra.setOk(ok);
				gra.setNome(nome);
				g.add(gra);
			}
		}
		return g;
	}
	public static ArrayList<Grafici> mediaTemperatura(ArrayList<StazioneMetereologica> stazione,int aggregazione,Timestamp t,double gradiente,double quota,String[] tipi,ControllerLingua locale) throws SQLException{
		double riferimentoG=0;
		boolean ok=false;
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<stazione.size();i++){
			StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica((stazione.get(i).getIdStazioneMetereologica()),locale);
			for(int k=0;k<tipi.length;k++){
				Grafici gra=new Grafici();
				ArrayList<Double> temperature=ControllerDatiClimatici.prendiT(t,s.getIdStazioneMetereologica(),aggregazione,tipi[k]);
				
				ok=ControllerDatiClimatici.controlloDati(temperature.size(),aggregazione,"temperatura_avg",s.getIdStazioneMetereologica());
				String nomeStazione=s.getNome();
				
				ArrayList<Double> gradienteT=new ArrayList<Double>();
				if(gradiente!=-9999 || gradiente!=0){
					double q=quota-s.ubicazione.getQuota();
					for(int j=0;j<temperature.size();j++){
						double tempo=temperature.get(j)-(q/100)*gradiente;
						gradienteT.add(tempo);
						System.out.println();
				}
				 riferimentoG=gradienteT.get(temperature.size()-1);
				 gradienteT.remove(temperature.size()-1);
			}
			double Triferimento=temperature.get(temperature.size()-1);
			temperature.remove(temperature.size()-1);
			ArrayList<Double> probabilita=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(temperature);
			double interpolazione=ControllerDatiClimatici.interpolazione(temperature, probabilita,Triferimento);
			gra.setX(temperature);
			gra.setY(probabilita);
			gra.setInterpolazione(interpolazione);
			String nome=""+s.getNome()+"-"+tipi[k];
			gra.setRiferimento(Triferimento);
			gra.setNome(nome);
			g.add(gra);
			if(gradiente!=-9999 || gradiente!=0){
				Grafici grad=new Grafici();
				grad.setX(gradienteT);
				grad.setY(probabilita);
				String n=""+s.getNome()+"-gradiente-"+tipi[k]+"";
				grad.setInterpolazione(interpolazione);
				grad.setRiferimento(riferimentoG);
				grad.setNome(n);
				g.add(grad);

			}		
		}}//
		
		return g;
	}
	
	public static ArrayList<Grafici> mediaPrecipitazioni(ArrayList<StazioneMetereologica> stazione,int finestra,int aggregazione,Timestamp t) throws SQLException{
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		//String data=Timestamp.toString(t);
		//System.out.println(""+id.length);
		boolean ok=false;
		for(int i=0;i<stazione.size();i++){
			Grafici gra=new Grafici();
			int idStazione=stazione.get(i).getIdStazioneMetereologica();
			double precrif=0;
			int anno=ControllerDatiClimatici.annoRiferimento(t, idStazione);
			ArrayList<Double> precipitazioni=ControllerDatiClimatici.prendiPrecipitazioni(t,aggregazione,idStazione);
			ok=ControllerDatiClimatici.controlloDati(precipitazioni.size(),aggregazione,"precipitazione",stazione.get(i).getIdStazioneMetereologica());
			String nomeStazione=stazione.get(i).getNome();
			if(ok==true){
			ArrayList<Double> somma=ControllerDatiClimatici.mediaMobilePrecipitazioni(precipitazioni,finestra,aggregazione*2+1,anno);			
			precrif=somma.get(somma.size()-1);
			somma.remove(somma.size()-1);	
			ArrayList<Double> pro=ControllerDatiClimatici.distribuzioneFrequenzaCumulativa(somma);
			double interpolazione=ControllerDatiClimatici.interpolazione(somma, pro,precrif);
			gra.setInterpolazione(interpolazione);
			String nome=ControllerDatabase.prendiNome(idStazione);
			gra.setNome(nome);
			gra.setRiferimento(precrif);
			gra.setX(somma);
			gra.setY(pro);
			gra.setOk(ok);
			g.add(gra);
		}
			else{
				gra.setOk(ok);
				gra.setNome(nomeStazione);
				g.add(gra);
			}
		}
		return g;
	}
	
	/*    QUERY    */
	
	public static String precipitazioniQuery(String anno,String[]id) throws SQLException{
		StringBuilder sb=new StringBuilder();
		String tipo="column";	
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		ArrayList<Dati> d=new ArrayList<Dati>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
				ArrayList<String> categorie=new ArrayList<String>();
				gra.setCategorie(categorie);
				 d=ControllerDatabase.prendiSommaPrecipitazioniMeseGiornaliero(id[i],anno);
				d=(controlloPrecipitazioni(d));
				gra.setY( datiADouble(d));
				g.add(gra);			
			Grafici cumulata=new Grafici();
			ArrayList<Double> cumulataY=new ArrayList<Double>();
			double somma=0;
			cumulata.setNome(""+nome+"-cumulata");
			for(int j=0;j<gra.getY().size();j++){
				somma+=gra.getY().get(j);
				cumulataY.add(somma);
			}
			cumulata.setY(cumulataY);
			g.add(cumulata);
		}
		String titolo="precipitazioni";
		String unita="mm";
		sb.append(""+HTMLElaborazioni.graficiMultipliPrecipitazioni( g, tipo, titolo,unita, unita, titolo,"cumulata",anno,"1",d));
		return sb.toString();
	}
	
	public static ArrayList<Dati> controlloPrecipitazioni(ArrayList<Dati> d){
		int cont=0;
		for(Dati dati:d){
			for(Double p:dati.getDati()){
				if(p!=-9999) {	
					cont++;
				}
			}
			if(cont<10) dati.setOk(true); 
		}
		return d;
	}
	
	public static ArrayList<Double> datiADouble(ArrayList<Dati> d){
		ArrayList<Double> v=new ArrayList<Double>();
		for(Dati da:d){
			for(Double a:da.getDati()){
				if(da.getOk()==false) v.add(0.0);
				else if(a!=-9999)v.add(a);
				else v.add(0.0);
			}
		}
		return v;
	}
	
	
	public static String precipitazioniQueryMese(String mese,String anno,String[]id) throws SQLException{
		StringBuilder sb=new StringBuilder();
		String tipo="column";
		Calendar ca=new GregorianCalendar();
		ca.set(Integer.parseInt(anno), Integer.parseInt(mese)-1, 1);
		int numero=ca.getActualMaximum(Calendar.DAY_OF_MONTH);
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		ArrayList<Dati> dati=new ArrayList<Dati>();
		for(int i=0;i<id.length;i++){
			Dati d=new Dati();
			Grafici gra=new Grafici();
			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			ArrayList<String> giorni=new ArrayList<String>();
			for(int j=0;j<numero;j++){
				giorni.add(""+(j+1));
			}
			gra.setCategorie(giorni);
			d.setDati(ControllerDatabase.prendiPrecipitazioniMeseMensile(id[i],anno,mese));
			dati.add(d);
			dati=(controlloPrecipitazioni(dati));
			
			gra.setY(datiADouble(dati));
			g.add(gra);
			Grafici cumulata=new Grafici();
			ArrayList<Double> cumulataY=new ArrayList<Double>();
			double somma=0;
			cumulata.setNome(""+nome+"-cumulata");
			for(int j=0;j<gra.getY().size();j++){
				somma+=gra.getY().get(j);
				cumulataY.add(somma);
			}
			cumulata.setY(cumulataY);
			g.add(cumulata);
		}
		String titolo="precipitazioni";
		String unita="mm";
		sb.append(""+HTMLElaborazioni.graficiMultipliPrecipitazioni( g, tipo, titolo,unita, unita, titolo,"cumulata",anno,mese,dati));
		return sb.toString();
	}
	
	public static String dateFormat(Calendar cal){
		String data = "" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH)+"";
		return data;
	}
	
	public static ArrayList<Grafici> precipitazioniQueryTrimestre(String mese,String anno,String[]id) throws SQLException{
		StringBuilder sb=new StringBuilder();
		String tipo="column";
		Calendar ca=new GregorianCalendar();
		ca.set(Integer.parseInt(anno), Integer.parseInt(mese)-1, 1);
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			

			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			
			ArrayList<String> giorni=new ArrayList<String>();
			int m=Integer.parseInt(mese);
			int a=Integer.parseInt(anno);
			gra.setY(ControllerDatabase.prendiPrecipitazioniTrimestreGiornaliero(id[i],anno,mese));
			
			Calendar c=new GregorianCalendar();
			c.set(a, m-1, 1);
			
			for(int j=0;j<gra.getY().size();j++){
				giorni.add(""+dateFormat(c));
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			gra.setCategorie(giorni);
			Grafici cumulata=new Grafici();
			ArrayList<Double> cumulataY=new ArrayList<Double>();
			double somma=0;
			cumulata.setNome(""+nome+"-cumulata");
			for(int j=0;j<gra.getY().size();j++){
				somma+=gra.getY().get(j);
				cumulataY.add(somma);
			}
			cumulata.setY(cumulataY);
			g.add(gra);
			g.add(cumulata);
		}
		
		return g;
	}
	
	
	public static String precipitazioniTemperaturaQueryAnno(String anno,String[]id) throws SQLException{
				StringBuilder sb=new StringBuilder();
		String tipo="column";
		
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			//if(aggregazione.equals("anno")){// se anno specifico
				ArrayList<String> categorie=new ArrayList<String>();
				String[] mesi={ "Jan",  "Feb", "Mar", "Apr", "May" ,"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
				for(int j=0;j<12;j++){
					categorie.add(mesi[j]);
				}
				gra.setCategorie(categorie);
				boolean a=true;
				gra.setY(ControllerDatabase.prendiSommaPrecipitazioniMese(id[i],anno,a));
				g.add(gra);	
			Grafici avg=new Grafici();
			avg.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"avg"));
			g.add(avg);
			Grafici min=new Grafici();
			min.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"min"));
			g.add(min);
			Grafici max=new Grafici();
			max.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"max"));
			g.add(max);
			Grafici maxMax=new Grafici();
			maxMax.setY(ControllerDatabase.prendiMM(id[i],anno,a,"max"));
			
			Grafici minMin=new Grafici();
			minMin.setY(ControllerDatabase.prendiMM(id[i],anno,a,"min"));
			g.add(maxMax);
			g.add(minMin);
			//}
			/*else{//se serie
				
					ArrayList<String> categorie=new ArrayList<String>();
				String[] mesi={ "Jan",  "Feb", "Mar", "Apr", "May" ,"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
				for(int j=0;j<12;j++){
						categorie.add(mesi[j]);
					}
					gra.setCategorie(categorie);
					boolean a=false;
					gra.setY(ControllerDatabase.prendiSommaPrecipitazioniMese(id[i],anno,a));
					g.add(gra);	
					
					Grafici avg=new Grafici();
					avg.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"avg"));
					g.add(avg);
					Grafici min=new Grafici();
					min.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"min"));
					g.add(min);
					Grafici max=new Grafici();
					max.setY(ControllerDatabase.prendiTemperatureAnno(id[i],anno,a,"max"));
					g.add(max);
					Grafici maxMax=new Grafici();
					maxMax.setY(ControllerDatabase.prendiMM(id[i],anno,a,"max"));
					
					Grafici minMin=new Grafici();
					minMin.setY(ControllerDatabase.prendiMM(id[i],anno,a,"min"));
					g.add(maxMax);
					g.add(minMin);
				
			}
			*/
			
			}
		
		String titolo="precipitazioni e temperatura";
		String unita="mm";
		String unita2="C";
		String titolo1="precipitazione";
		String titolo2="temperatura";
		sb.append(""+HTMLElaborazioni.graficiMultipli( g, tipo, titolo,unita, unita2, titolo1,titolo2));
		return sb.toString();
	}
	
	public static ArrayList<Grafici> queryTemperaturaAnno(String anno,String[]id) throws NumberFormatException, SQLException{
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
		Grafici gra=new Grafici();
		String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
		gra.setNome(nome);
			
			boolean a=true;
			
		Grafici avg=new Grafici();
		avg.setY(ControllerDatabase.temperatureAnno(id[i],anno,a,"avg"));
		avg.setNome("avg");
		System.out.println("avg size="+avg.getY().size());
		g.add(avg);
		Grafici min=new Grafici();
		min.setY(ControllerDatabase.temperatureAnno(id[i],anno,a,"min"));
		System.out.println("min size="+min.getY().size());

		min.setNome("min");
		g.add(min);
		Grafici max=new Grafici();
		max.setY(ControllerDatabase.temperatureAnno(id[i],anno,a,"max"));
		System.out.println("max size="+max.getY().size());

		max.setNome("max");
		g.add(max);
		
		}
		String unita="C";
		String titolo="temperatura";
		return g;
	}
	
	public static ArrayList<Grafici> queryTemperaturaTrimestre(String mese,String anno,String[]id) throws NumberFormatException, SQLException{
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<id.length;i++){
			Grafici gra=new Grafici();
			String nome=ControllerDatabase.prendiNome(Integer.parseInt(id[i]));
			gra.setNome(nome);
			
			boolean a=true;
			
			Grafici avg=new Grafici();
			avg.setY(ControllerDatabase.temperatureTrimestre(id[i],anno,mese,a,"avg"));
			avg.setNome("avg");
			System.out.println("PROVA="+avg.getY().size());
			g.add(avg);
			Grafici min=new Grafici();
			min.setY(ControllerDatabase.temperatureTrimestre(id[i],anno,mese,a,"min"));
			min.setNome("min");
			g.add(min);
			Grafici max=new Grafici();
			max.setY(ControllerDatabase.temperatureTrimestre(id[i],anno,mese,a,"max"));
			max.setNome("max");
			g.add(max);
		
		}
		String unita="C";
		String titolo="temperatura";
		return g;
	}
}
