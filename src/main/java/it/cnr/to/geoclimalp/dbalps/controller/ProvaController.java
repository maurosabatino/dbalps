package it.cnr.to.geoclimalp.dbalps.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import it.cnr.to.geoclimalp.dbalps.bean.Dati;
import it.cnr.to.geoclimalp.dbalps.bean.Grafici;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;

public class ProvaController {
	
	static String url = "jdbc:postgresql://localhost:5432/dbalps";
	static String user = "postgres";
	static String pwd = "guido2014";
	
	/*           GENERICI         */
	public static double arrotonda(double n){
		/*	double d2 = (int)(n*10);
			d2=Math.round(d2);
			 d2 /= 10;*/
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
		//System.out.println(		"  temperatura="+temperature.size());
		for(t=0;t<temperature.size()-1;t++){
			if(temperature.get(t).compareTo(riferimento)==0) return prob.get(p);
			else if(temperature.get(t).compareTo(riferimento)<0 && 0<temperature.get(t+1).compareTo(riferimento)){
					risultato=((riferimento-temperature.get(t+1))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p)-((riferimento-temperature.get(t))/(temperature.get(t)-temperature.get(t+1)))*prob.get(p+1);
					risultato=arrotondaPerEccesso(risultato,4);
					System.out.println("inter:"+risultato);
					return risultato;
			}
			if(t==temperature.size()-1 || temperature.get(t).compareTo(temperature.get(t+1))!=0) {
				p++;
			}		
		}
		if(riferimento>temperature.get(temperature.size()-1)) return 1;
		else return -1;
	}
	public static int dataLimite(Timestamp d,int limite){
		int data = 0;
		Calendar cal=new GregorianCalendar();
		//cal.setTimeInMillis(d.getTime());
		cal.setTime(d);
		//System.out.println("cal="+cal.getTime());
		//cal.add(Calendar.MONTH, -1);
		cal.add(Calendar.DAY_OF_YEAR, limite);
		//cal.add(Calendar.MONTH, 1);
		//System.out.println("cal="+cal.getTime());
		int mese=cal.get(Calendar.MONTH)+1;
		if(cal.get(Calendar.DAY_OF_MONTH)<10){
			data=Integer.parseInt(""+mese+"0"+cal.get(Calendar.DAY_OF_MONTH));
		}
			
		else{
			data=Integer.parseInt(""+mese+""+cal.get(Calendar.DAY_OF_MONTH));
		}
			

		return data;
	}
	public static int dataNuova(Timestamp d){
		int data = 0;
		Calendar cal=new GregorianCalendar();	
		cal.setTime(d);
		int mese=cal.get(Calendar.MONTH)+1;
		if(cal.get(Calendar.DAY_OF_MONTH)<10){
			data=Integer.parseInt(""+mese+"0"+cal.get(Calendar.DAY_OF_MONTH));
		}
		else{
			data=Integer.parseInt(""+mese+""+cal.get(Calendar.DAY_OF_MONTH));
		}
		return data;
	}
	
	public static int annoRiferimento(Timestamp t, int id) throws SQLException{
		int anno=0;
		Calendar cal=new GregorianCalendar();
		cal.setTime(t);
		anno=cal.get(Calendar.YEAR);
		System.out.println("anno:"+anno);
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Double> tem= new ArrayList<Double>();
		ResultSet rs=st.executeQuery("select count(distinct date_part('year',data) ) from temperatura_avg where date_part('year',data)<"+anno+" and idstazionemetereologica="+id+"" );
		while(rs.next()) anno=rs.getInt("count");
		anno=anno+1;
		rs.close();
		st.close();
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
		double t=0;
		for(int i=0;i<dati.size();i++){
			if(i!=dati.size()-1){
			 if((dati.get(i).equals(dati.get(i+1)))==false){
				 t=dati.get(i+1);
				 cont=1;
				probabilita=(double)(i+1)/(double)(dati.size()+1);
				probabilita=arrotondaPerEccesso(probabilita,4);
				temp.add(probabilita);
				}else 	cont++;
		}else{
			probabilita=(double)(i+1)/(double)(dati.size()+1);
			probabilita=arrotondaPerEccesso(probabilita,4);
			temp.add(probabilita);
		}		
		} 
		return temp;
		}
	
	public static double arrotondaPerEccesso(double value, int numCifreDecimali) {
	      double temp = Math.pow(10, numCifreDecimali);
	      return Math.round(value * temp) / temp;
	   }
	
	/*-------------------------deltaT-------------------------*/
	
	public static ArrayList<Grafici> deltaT(ArrayList<StazioneMetereologica> stazione,int finestra,int aggregazione,Timestamp t,ControllerLingua locale) throws SQLException{
		String sb = "";
		ArrayList<Grafici> g=new ArrayList<Grafici>();	
		//cambiare inserimenti metodi controller dati climatici
		double deltarif=0;
		double interpolazione=0;
		boolean ok=false;	
		for(int i=0;i<stazione.size();i++){
			StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica(stazione.get(i).getIdStazioneMetereologica(),locale);
			int anno=ControllerDatiClimatici.annoRiferimento(t, s.getIdStazioneMetereologica());
			Grafici gra=new Grafici();
			ArrayList<Dati> temperature = ProvaController.prendiTDelta(t, aggregazione,s.getIdStazioneMetereologica());
			String nome=s.getNome();
				ArrayList<Double> deltaT=ProvaController.mediaMobileDeltaT(temperature,finestra,aggregazione,anno);
				deltarif=deltaT.get(deltaT.size()-1);
				deltaT.remove(deltaT.size()-1);
				ArrayList<Double> probabilita=ProvaController.distribuzioneFrequenzaCumulativa(deltaT);
				interpolazione=ProvaController.interpolazione(deltaT, probabilita,deltarif);
				gra.setInterpolazione(interpolazione);
				gra.setNome(nome);
				gra.setRiferimento(deltarif);
				gra.setX(deltaT);
				gra.setY(probabilita);
				gra.setOk(true);
				g.add(gra);
		}
		return g;
	}
	
	public static ArrayList<Double> mediaMobileDeltaT(ArrayList<Dati> dati, int finestra,int aggregazione,int annoriferimento){
		// finestra
		// aggregazione giorni tot
		ArrayList<Double> a = new ArrayList<Double>();
		double deltaT=0;
		double deltarif=0;
		for(Dati da:dati){
			for(int i=0;i<da.getDati().size()-finestra+1;i++){	
				if(annoriferimento==da.getAnno() && (i+finestra-1)==(aggregazione)){
					deltarif=da.getDati().get(i+finestra-1)-da.getDati().get(i);
					//deltarif=arrotondaPerEccesso(deltarif,4);
				}
				else{
					if(da.getDati().get(i)!=-9999 && da.getDati().get(i+finestra-1)!=-9999 ){
						deltaT=da.getDati().get(i+finestra-1)-da.getDati().get(i);
						//deltaT=arrotondaPerEccesso(deltaT,4);
						a.add(deltaT);
					}
				}
			}
		}
	Collections.sort(a);
	a.add(deltarif);
	return a;
	}
	
	public static ArrayList<Dati> prendiTDelta(Timestamp t,int limite, int id) throws SQLException{// limite = intervallo a dx/sx es 15 su aggregazione 30 giorni
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Dati> tem= new ArrayList<Dati>();
		int limiteinf=dataLimite(t,-limite);
		int limitesup=dataLimite(t,limite);
		int anno=0;
		Dati d=null;
		ResultSet  rs;
		if(limiteinf<limitesup){
			rs =st.executeQuery("SELECT temperaturaavg,EXTRACT(year FROM data) as anno FROM temperatura_avg WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");
			while(rs.next()){
				int a=Integer.parseInt(rs.getString("anno"));
				if(tem.isEmpty()){
					d=new Dati(a);
					d.getDati().add(rs.getDouble("temperaturaavg"));
					tem.add(d);
				}
				else{
					if(a==d.getAnno()){
						d.getDati().add(rs.getDouble("temperaturaavg"));
					}
					else{
						d=new Dati(a);
						d.getDati().add(rs.getDouble("temperaturaavg"));
						tem.add(d);					
					}				
				}	
			}
		}else{
			Calendar cal=new GregorianCalendar();
			cal.setTime(t);
			System.out.println(""+cal.get(Calendar.MONTH));
			if(cal.get(Calendar.MONTH)<6){			
				rs =st.executeQuery("SELECT temperaturaavg,data,(EXTRACT(year FROM data)) as anno FROM temperatura_avg"+ 
					   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND 1231 and idstazionemetereologica="+id+" and EXTRACT(year FROM data)>ANY(select min(EXTRACT(year FROM data)) from temperatura_avg where idstazionemetereologica="+id+") "+
					   " union all SELECT temperaturaavg,data,(EXTRACT(year FROM data)) as anno FROM temperatura_avg "+
					   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN 101 AND "+limitesup+" and idstazionemetereologica="+id+" order by data");
			}
			else {	
				rs =st.executeQuery("SELECT temperaturaavg,data,(EXTRACT(year FROM data)) as anno FROM temperatura_avg"+ 
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND 1231 and idstazionemetereologica="+id+" "+
						   " union all SELECT temperaturaavg,data,(EXTRACT(year FROM data)) as anno FROM temperatura_avg "+
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN 101 AND "+limitesup+" and EXTRACT(year FROM data)>ANY(select min(EXTRACT(year FROM data)) from temperatura_avg where idstazionemetereologica="+id+") and idstazionemetereologica="+id+" order by data");			
			}
			 while(rs.next()){
				 String controllo=(rs.getString("data"));
				 Timestamp c;
				 c=Timestamp.valueOf(controllo);	
				 int datac=ProvaController.dataNuova(c);
				 if(tem.isEmpty()){
						d=new Dati();
						d.getDati().add(rs.getDouble("temperaturaavg"));
						anno=Integer.parseInt(rs.getString("anno"));
						d.setAnno(anno);
						tem.add(d);
					}
				 else{
					 if(datac!=limiteinf){
						 d.getDati().add(rs.getDouble("temperaturaavg"));
					 }
					 else{
						// tem.add(d);
						 d=new Dati();
						 anno=Integer.parseInt(rs.getString("anno"));
						 d.setAnno(anno);
						 d.getDati().add(rs.getDouble("temperaturaavg"));
						 tem.add(d);
					 }
				 }
			 }		
		}
		for(Dati da:tem){
			System.out.println("size:"+da.getDati().size());
		}
		rs.close();
		st.close();
		return tem;	
	}
	
	/*---------------precipitazioni--------------------*/
	
	public static ArrayList<Dati> prendiPrec(Timestamp t,int limite,int id) throws SQLException{// limite = intervallo a dx/sx es 15 su aggregazione 30 giorni
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Dati> prec= new ArrayList<Dati>();
		int limiteinf=ProvaController.dataLimite(t,-limite);
		int limitesup=ProvaController.dataLimite(t,limite);
		int anno=0;
		Dati d=null;
		ResultSet rs;
		if(limitesup>limiteinf){
			System.out.println(	("SELECT quantita,EXTRACT(year FROM data) as anno FROM precipitazione WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data"));	

			rs =st.executeQuery("SELECT quantita,EXTRACT(year FROM data) as anno FROM precipitazione WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");	
		  while(rs.next()){
			int a=Integer.parseInt(rs.getString("anno"));
			if(prec.isEmpty()){
				d=new Dati(a);
				d.getDati().add(rs.getDouble("quantita"));
				prec.add(d);
			}
			else{
				if(a==d.getAnno()){
					d.getDati().add(rs.getDouble("quantita"));

				}
				else{
					d=new Dati(a);
					d.getDati().add(rs.getDouble("quantita"));
					prec.add(d);					
				}				
			}	
		}
		}else{
			Calendar cal=new GregorianCalendar();
			cal.setTime(t);
			if(cal.get(Calendar.MONTH)<6){
				rs =st.executeQuery("SELECT quantita,data,(EXTRACT(year FROM data)) as anno FROM precipitazione"+ 
					   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND 1231 and idstazionemetereologica="+id+" and EXTRACT(year FROM data)>ANY(select min(EXTRACT(year FROM data)) from precipitazione where idstazionemetereologica="+id+") "+
					   " union all SELECT quantita,data,(EXTRACT(year FROM data)) as anno FROM precipitazione "+
					   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN 101 AND "+limitesup+" and idstazionemetereologica="+id+" order by data");
			}
			else {
				rs =st.executeQuery("SELECT quantita,data,(EXTRACT(year FROM data)) as anno FROM precipitazione"+ 
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND 1231 and idstazionemetereologica="+id+" "+
						   " union all SELECT quantita,data,(EXTRACT(year FROM data)) as anno FROM precipitazione "+
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN 101 AND "+limitesup+" and EXTRACT(year FROM data)>ANY(select min(EXTRACT(year FROM data)) from precipitazione where idstazionemetereologica="+id+") and idstazionemetereologica="+id+" order by data");
			}
			 while(rs.next()){
				 String controllo=(rs.getString("data"));
				 Timestamp c;
				 c=Timestamp.valueOf(controllo);
				 int datac=ProvaController.dataNuova(c);
				 if(prec.isEmpty()){
						d=new Dati();
						d.getDati().add(rs.getDouble("quantita"));
						anno=Integer.parseInt(rs.getString("anno"));
						d.setAnno(anno);
						prec.add(d);
					}
				 else{ 
					 if(datac==limiteinf){
						 d=new Dati();
						 anno=Integer.parseInt(rs.getString("anno"));
						 d.setAnno(anno);
						 d.getDati().add(rs.getDouble("quantita"));
						 prec.add(d);
					 }
					 else{
						 d.getDati().add(rs.getDouble("quantita"));
					 }
				 }
			 }
		}
		rs.close();
		st.close();
		return prec;
	}
	
	public static ArrayList<Double> mediaMobilePrecipitazioni(ArrayList<Dati> dati, int finestra,int aggregazione,int annoriferimento){// riferimento la posizione della temperatura del giorno scelto
		// finestra
		// aggregazione giorni tot
		ArrayList<Double> prec = new ArrayList<Double>();
		double precrif=0;
		double somma=0;	
		int cont=0;
		for(Dati da:dati){
			for(int i=0;i<da.getDati().size()-finestra+1;i++){
				for(int j=0;j<finestra;j++){
					if(da.getDati().get(i+j)==-9999 ) da.getDati().set(i+j,0.0);	
					somma=somma+da.getDati().get(i+j);
					//somma=arrotondaPerEccesso(somma,4);
				}
				if(somma!=0.00){	
					if((annoriferimento==da.getAnno() && (i+finestra-1)==(aggregazione)) || ( aggregazione<=finestra && annoriferimento==da.getAnno())   ){
						precrif=somma;		
						System.out.println("anno="+da.getAnno());
						//precrif=arrotondaPerEccesso(precrif,4);
					}
					else{
						prec.add(somma);								
					}
			}else{
				cont++;
			}
				somma=0;		
			}
		}		 
		Collections.sort(prec);
		prec.add(precrif);
		return prec;
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
			ArrayList<Dati> precipitazioni=ProvaController.prendiPrec(t,aggregazione,idStazione);
			//ok=ControllerDatiClimatici.controlloDati(precipitazioni.size(),aggregazione,"precipitazione",stazione.get(i).getIdStazioneMetereologica());
			String nomeStazione=stazione.get(i).getNome();
			ArrayList<Double> somma=ProvaController.mediaMobilePrecipitazioni(precipitazioni,finestra,aggregazione,anno);			
			precrif=somma.get(somma.size()-1);
			somma.remove(somma.size()-1);	
			ArrayList<Double> pro=ProvaController.distribuzioneFrequenzaCumulativa(somma);
			double interpolazione=ProvaController.interpolazione(somma, pro,precrif);
			gra.setInterpolazione(interpolazione);
			String nome=ControllerDatabase.prendiNome(idStazione);
			gra.setNome(nome);
			gra.setRiferimento(precrif);
			gra.setX(somma);
			gra.setY(pro);
			gra.setOk(true);
			g.add(gra);	
		}
		return g;
	}
	
	/*-----------------temperature------------------*/
	
	
	public static ArrayList<Dati> prendiT(Timestamp d,int id,int limite,String tipo) throws SQLException{
		double riferimento=0;
		Connection conn = DriverManager.getConnection(url,user,pwd);
		Statement st = conn.createStatement();
		ArrayList<Dati> tem= new ArrayList<Dati>();
		/*Calendar cal=new GregorianCalendar();
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1);*/
		int limiteinf=dataLimite(d,-limite);
		int limitesup=dataLimite(d,0);
		ResultSet rs;
		int annoriferimento=ControllerDatiClimatici.annoRiferimento(d, id);
		double cont=0;
		double med=0;
		int anno=0;
		boolean rif=false;
		Dati da=null;
		if(limiteinf<=limitesup){
			if(limiteinf==limitesup)  rs=st.executeQuery("SELECT temperatura"+tipo+",data,EXTRACT(year FROM data) as anno FROM temperatura_"+tipo+" WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int="+limiteinf+" and idstazionemetereologica="+id+" order by data");		
			else rs =st.executeQuery("SELECT temperatura"+tipo+",data,EXTRACT(year FROM data) as anno FROM temperatura_"+tipo+" WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");		
			while(rs.next()){
			  int a=Integer.parseInt(rs.getString("anno"));
			if(tem.isEmpty()){
				da=new Dati(a);
				da.getDati().add(rs.getDouble("temperatura"+tipo));
				tem.add(da);
			}
			else{
				if(a==da.getAnno()){
					da.getDati().add(rs.getDouble("temperatura"+tipo));
				}
				
				else{
					da=new Dati(a);
					da.getDati().add(rs.getDouble("temperatura"+tipo));
					tem.add(da);					
				}				
			}	
		}
		}else{
			Calendar cal=new GregorianCalendar();
			cal.setTime(d);
			if(cal.get(Calendar.MONTH)<6){
				rs =st.executeQuery("SELECT temperatura"+tipo+",data,(EXTRACT(year FROM data)) as anno FROM temperatura_"+tipo+""+ 
					   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND 1231 and idstazionemetereologica="+id+" and EXTRACT(year FROM data)>ANY(select min(EXTRACT(year FROM data)) from temperatura_"+tipo+" where idstazionemetereologica="+id+") "+
					   " union all SELECT temperatura"+tipo+",data,(EXTRACT(year FROM data)) as anno FROM temperatura_"+tipo+" "+
					   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN 101 AND "+limitesup+" and idstazionemetereologica="+id+"  order by data");
			}
			else {
				rs =st.executeQuery("SELECT temperatura"+tipo+",data,(EXTRACT(year FROM data)) as anno FROM temperatura_"+tipo+""+ 
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND 1231 and idstazionemetereologica="+id+"  "+
						   " union all SELECT temperatura"+tipo+",data,(EXTRACT(year FROM data)) as anno FROM temperatura_"+tipo+""+
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN 101 AND "+limitesup+" and EXTRACT(year FROM data)>ANY(select min(EXTRACT(year FROM data)) from temperatura_"+tipo+" where idstazionemetereologica="+id+") and idstazionemetereologica="+id+"  order by data");
			}		
			while(rs.next()){
				 String controllo=(rs.getString("data"));
				 Timestamp c;
				 c=Timestamp.valueOf(controllo);
				 int datac=ProvaController.dataNuova(c);
				 if(tem.isEmpty()){
						da=new Dati();
						da.getDati().add(rs.getDouble("temperatura"+tipo));
						anno=Integer.parseInt(rs.getString("anno"));
						da.setAnno(anno);
						tem.add(da);
					}
				 else{ 
					 if(datac==limiteinf){
						 da=new Dati();
						 anno=Integer.parseInt(rs.getString("anno"));
						 da.setAnno(anno);
						 da.getDati().add(rs.getDouble("temperatura"+tipo));
						 tem.add(da);
					 }
					 else{
						 da.getDati().add(rs.getDouble("temperatura"+tipo));

					 }
				 }}
		}
		rs.close();
		st.close();
		return tem;
	}
	
	public static ArrayList<Grafici> mediaTemperatura(ArrayList<StazioneMetereologica> stazione,int aggregazione,Timestamp t,double gradiente,double quota,String[] tipi,ControllerLingua locale) throws SQLException{
		double riferimentoG=0;
		boolean ok=false;
		ArrayList<Grafici> g=new ArrayList<Grafici>();
		for(int i=0;i<stazione.size();i++){
			StazioneMetereologica s=ControllerDatabase.prendiStazioneMetereologica((stazione.get(i).getIdStazioneMetereologica()),locale);
			for(int k=0;k<tipi.length;k++){
				Grafici gra=new Grafici();
				ArrayList<Dati> da=ProvaController.prendiT(t,s.getIdStazioneMetereologica(),aggregazione,tipi[k]);
				int anno=ControllerDatiClimatici.annoRiferimento(t, s.getIdStazioneMetereologica());
				ArrayList<Double> temperature=mediaMobileTemperatura(da,anno);
				//ok=ControllerDatiClimatici.controlloDati(temperature.size(),aggregazione,"temperatura_avg",s.getIdStazioneMetereologica());
				String nomeStazione=s.getNome();				
				ArrayList<Double> gradienteT=new ArrayList<Double>();
				if(gradiente!=-9999 || gradiente!=0){
					double q=quota-s.ubicazione.getQuota();
					for(int j=0;j<temperature.size();j++){
						double tempo=temperature.get(j)-(q/100)*gradiente;
						gradienteT.add(tempo);
					}
				 riferimentoG=gradienteT.get(temperature.size()-1);
				 gradienteT.remove(temperature.size()-1);
				}
			double Triferimento=temperature.get(temperature.size()-1);
			temperature.remove(temperature.size()-1);
			ArrayList<Double> probabilita=ProvaController.distribuzioneFrequenzaCumulativa(temperature);
			double interpolazione=ProvaController.interpolazione(temperature, probabilita,Triferimento);
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
	
	public static ArrayList<Double> mediaMobileTemperatura(ArrayList<Dati> d,int annoriferimento){
		ArrayList<Double> t=new ArrayList<Double>();
		int cont=0;
		double med=0;
		double riferimento=0;
		for(Dati da:d){			
			for(Double tem:da.getDati()){
				System.out.println("dato="+da.getDati());
				if(tem!=-9999){
					med=med+tem;
					cont++;
					System.out.println("med="+med);
				}
			}
			if(da.getAnno()==annoriferimento){
				riferimento=med/cont;
				riferimento=arrotondaPerEccesso(riferimento, 4);
			}
			else if(!(da.getDati().size()==1 &&da.getDati().get(0)==-9999) ){
				med=med/cont;
				med=arrotondaPerEccesso(med, 4);
				t.add(med);
			}
			cont=0;
			med=0;
		}
		Collections.sort(t);
		t.add(riferimento);
		return t;
	}
	
/*	public static void main(String [] args) throws SQLException{
		Timestamp t= Timestamp.valueOf("2013-09-30 00:00:00");
		ArrayList<StazioneMetereologica> staz = new  ArrayList<StazioneMetereologica>();
		staz.add(ControllerDatabase.prendiStazioneMetereologica(12, "IT"));
		String[] tipi=new String[1];
		tipi[0]="avg";
		mediaPrecipitazioni(staz,91,45,t);
		
	}
	*/
	
	
	
}
