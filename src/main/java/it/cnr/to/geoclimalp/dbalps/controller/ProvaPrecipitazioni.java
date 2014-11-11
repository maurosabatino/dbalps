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
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;

public class ProvaPrecipitazioni {
	static String url = "jdbc:postgresql://localhost:5432/dbalps";
	static String user = "postgres";
	static String pwd = "guido2014";
	
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
			rs =st.executeQuery("SELECT quantita,EXTRACT(year FROM data) as anno FROM precipitazione WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND "+limitesup+" and idstazionemetereologica="+id+" order by data");	
		  while(rs.next()){
			int a=Integer.parseInt(rs.getString("anno"));
			if(prec.isEmpty()){
				d=new Dati(a);
				d.getDati().add(rs.getDouble("quantita"));
				System.out.print("db:"+rs.getDouble("quantita"));
				prec.add(d);
			}
			else{
				if(a==d.getAnno()){
					d.getDati().add(rs.getDouble("quantita"));
					System.out.print("db:"+rs.getDouble("quantita"));

				}
				else{
					d=new Dati(a);
					d.getDati().add(rs.getDouble("quantita"));
					System.out.print("db:"+rs.getDouble("quantita"));

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
				
				System.out.println("SELECT quantita,data,(EXTRACT(year FROM data)) as anno FROM precipitazione"+ 
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN "+limiteinf+" AND 1231 and idstazionemetereologica="+id+" "+
						   " union all SELECT quantita,data,(EXTRACT(year FROM data)) as anno FROM precipitazione "+
						   " WHERE  (EXTRACT(MONTH FROM data) * 100 + EXTRACT(DAY FROM data))::int BETWEEN 101 AND "+limitesup+" and EXTRACT(year FROM data)>ANY(select min(EXTRACT(year FROM data)) from precipitazione where idstazionemetereologica="+id+") and idstazionemetereologica="+id+" order by data");
				
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
	
	public static ArrayList<Double> mediaMobilePrecipitazioni(ArrayList<Dati> dati, int finestra,int aggregazione,int annoriferimento){// riferimento  la posizione della temperatura del giorno scelto
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
					//System.out.println(somma);
				}
				if(somma!=0.00){
					
					if(annoriferimento==da.getAnno() && ( (i+finestra-1)==(aggregazione) || aggregazione*2<finestra) ){
						precrif=somma;
						//precrif=arrotondaPerEccesso(precrif,4);
					}
					else prec.add(somma);								
			}else{
				System.out.println("ho saltato");
				cont++;
			}
				somma=0;		
			}
		}
		 
		Collections.sort(prec);
		
		System.out.println("finestre"+prec.size()+" zero"+cont);
		prec.add(precrif);
		return prec;
	}

	/*public static void main(String [] args) throws SQLException{
		Timestamp t= Timestamp.valueOf("2013-11-22 00:00:00");
		ArrayList<StazioneMetereologica> staz = new  ArrayList<StazioneMetereologica>();
		staz.add(ControllerDatabase.prendiStazioneMetereologica(15, "IT"));
		
		ArrayList<Dati> precipiatzioniDB = prendiPrec(t, 45, 15);
		
		ArrayList<Double> mediaMobile = mediaMobilePrecipitazioni(precipiatzioniDB, 30, 45,2013);
		
	
	}*/

}
