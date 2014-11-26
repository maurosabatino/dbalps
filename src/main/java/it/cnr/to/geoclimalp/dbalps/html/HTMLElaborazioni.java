package it.cnr.to.geoclimalp.dbalps.html;

import java.sql.SQLException;
import java.util.ArrayList;

import it.cnr.to.geoclimalp.dbalps.bean.Dati;
import it.cnr.to.geoclimalp.dbalps.bean.Grafici;
import it.cnr.to.geoclimalp.dbalps.bean.stazione.*;


public class HTMLElaborazioni {
	
	
	
	public static String grafici(ArrayList<Grafici> gra,String titolo,String unita){
		StringBuilder sb= new StringBuilder();
		sb.append("<script src=\"js/jquery-1.10.2.js\"></script>" );
		sb.append(		"		<script src=\"js/Charts/highcharts.js\"></script>" );
		sb.append(		"		<script src=\"js/Charts/modules/exporting.js\"></script>");
		sb.append(		"		<script type=\"text/javascript\">" );
		sb.append(		"		$(function () {" );
		sb.append(		"		    $('#container').highcharts({" );
		sb.append(		"		        title: {" );
		sb.append(		"		            text: '"+titolo+"'," );
		sb.append(		"		        }," );
		sb.append(		"		        subtitle: {" );
		sb.append(		"		            text: 'elaborazioni '," );
        sb.append(		"		        }," );
		sb.append(		"		        xAxis: {" );
		sb.append(		"					title: {" );
		sb.append(		"		                text: '"+titolo+""+unita+" '" );
		sb.append(		"		            }" );
		sb.append(				"},	yAxis: {" );
		sb.append(			"		            title: {" );
		sb.append(			"		                text: 'Probabilità '" );
		sb.append(			"		            }," );
		sb.append(			"		            plotLines: [{" );
		sb.append(			"		                value: 0," );
		sb.append(			"		                width: 1," );
		sb.append(			"		                color: '#808080'" );
		sb.append(		"		            }]," );
		sb.append(		"min:0," );
		sb.append(		" max:1,");
		sb.append(			"		        }," );
        sb.append(			"		        legend: {" );
		sb.append(			"		            layout: 'vertical'," );
		sb.append(				"		            align: 'right'," );
		sb.append(				"		            verticalAlign: 'middle'," );
		sb.append(				"		            borderWidth: 0");
		sb.append(				"		        },");
		sb.append("		        series: [");
		for(Grafici g:gra){
			    sb.append("	{" );
				sb.append(		"		            name: '"+g.getNome()+"'," );
				sb.append(		"marker: {");
				sb.append(        "  enabled: false");
				sb.append(       "},");			
                sb.append(		"		            data: ["); 	
			int cont=0;
		
			for(int i=0;i<g.getX().size();i++){	
				if(i!=(g.getX().size()-1)){
					if((g.getX().get(i).equals(g.getX().get(i+1)))==false){
						sb.append("["+g.getX().get(i)+","+g.getY().get(cont)+"],");
						cont++;
					}
					}else{
						sb.append("["+g.getX().get(i)+","+g.getY().get(cont)+"],");
						cont++;
				}      
						
			}	
				sb.append("],");
			    sb.append("},{" );
				sb.append(		"name: '"+g.getNome()+"-riferimento'," );
				sb.append(		"marker:{" );
				sb.append(		"symbol: 'circle'},");					
                sb.append(		"          color:'red',");
		        sb.append(	"data: [["+g.getRiferimento()+","+g.getInterpolazione()+"]]}," );
		}
			sb.append(		"]" );	
			sb.append(	"	    });" );
			sb.append(	"		});" );
			sb.append(	"</script>" );
			sb.append(	"<div id=\"container\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div> ");
			sb.append("<a href=\"Servlet?operazione=download&grafici=grafici\">dettagli</a>");
			sb.append("<form action=\"Servlet\" name=\"download\" method=\"POST\" >");
	        sb.append(" <input type=\"submit\" name =\"submit\" value=\"download\" >");
			sb.append(" <input type=\"hidden\" name=\"operazione\" value=\"download\">");
			sb.append(" <input type=\"hidden\" name=\"titolo\" value=\""+titolo+"\">");
			sb.append(" </form>");
			
		return sb.toString();
	}
	
	
	public static String sceltaQuery(){
		StringBuilder sb=new StringBuilder();
		sb.append("	<div class=\"row\">");
		sb.append("	<div class=\"col-xs-6 col-md-11 col-md-push-1\"><h3>Query sui dati climatici</h3></div>");
		sb.append("	</div>");
		sb.append("	<br>");
		sb.append("	<div class=\"list-group\">");
		sb.append("	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=precipitazioniAnno\" class=\"list-group-item\">  precipitazioni anni</a></div>");
		sb.append("  	</div>	");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=precipitazioniMese\" class=\"list-group-item\"> precipitazioni mese</a></div>");
		sb.append("  	</div>");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=precipitazioniTrimestre\" class=\"list-group-item\"> precipitazioni trimestre</a></div>");
		sb.append("  	</div>");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=temperaturaEPrecipitazioneAnno\" class=\"list-group-item\"> temperatura e precipitazione anno</a></div>");
		sb.append("  	</div>");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=temperaturaTrimestre\" class=\"list-group-item\"> temperatura trimestre</a></div>");
		sb.append("  	</div>");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=temperaturaAnno\" class=\"list-group-item\"> temperatura anno</a></div>");
		sb.append("  	</div>");
		sb.append("  	</div>");

		return sb.toString();
	}
	
	public static String datiAnno(ArrayList<StazioneMetereologica>s, String op){
		StringBuilder sb=new StringBuilder();
		sb.append("<script  type=\"text/javascript\">");
		/*sb.append("function Disabilita(stato1,stato2,stato3){");
		sb.append("	document.getElementById('anno').disabled = stato1;");
		sb.append("	document.getElementById('mesi').disabled = stato2;");
		sb.append("	document.getElementById('anni').disabled = stato3;");
*/
		
		sb.append("	</script>");
		sb.append(HTMLScript.controlloAnno());
		
           
		sb.append("<form action=\"Servlet\" onSubmit=\"return verificaAnno(this);\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th> <th>scelto</th> </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td> <td> <input type=\"checkbox\" name=\"id\" value=\""+stazione.getIdStazioneMetereologica()+"\" checked=\"checked\" >  </td> </tr>");
		}
		sb.append("</table>");
		//sb.append("serie <input type=\"radio\" name=\"aggregazione\" value=\"serie\" onClick=\"Disabilita(true,false,false);\"/>");
		//sb.append(" anno<input type=\"radio\" name=\"aggregazione\" value=\"anno\" onClick=\"Disabilita(false,true,true);\"/>");
		 sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
                sb.append("<p>anno:<input type=\"text\" id=\"anno\" name=\"anno\"   \"></p>");
		
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
                sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("</form>");
		return sb.toString();	
		}
	
	public static String DatiTrimestre(ArrayList<StazioneMetereologica>s,String op){
		StringBuilder sb=new StringBuilder();
		
		sb.append("<form action=\"Servlet\" name=\"dati\" method=\"POST\">");
		sb.append("<table class=\"table\"> <tr> <th>nome</th> <th>scelto</th> </tr>");
		for(StazioneMetereologica stazione: s){
			sb.append(" <tr> <td>"+stazione.getNome()+" </td> <td> <input type=\"checkbox\" name=\"id\" value=\""+stazione.getIdStazioneMetereologica()+"\" checked=\"checked\" >  </td> </tr>");
		}
		sb.append("</table>");
                 sb.append("<div class=\"panel panel-default\"> <div class=\"panel-body\"> <h4>Dati elaborazione</h4>");
		sb.append("<div class=\"row\">");
		sb.append("<select name=\"mese\" >" );
		sb.append("<option value=0> Seleziona un'opzione</option>");
		sb.append("<option value='1'> JAN </option>");
		sb.append("<option value='2'> FEB </option>");
		sb.append("<option value='3'> MAR </option>");
		sb.append("<option value='4'> APR </option>");
		sb.append("<option value='5'> MAY </option>");
		sb.append("<option value='6'> JUN </option>");
		sb.append("<option value='7'> JUL </option>");
		sb.append("<option value='8'> AUG </option>");
		sb.append("<option value='9'> SEPT </option>");
		sb.append("<option value='10'> OCT </option>");
		sb.append("<option value='11'> NOV </option>");
		sb.append("<option value='12'> DEC </option>");
		sb.append("</select>");
		sb.append("<p>anno:<input type=\"text\" id=\"anno\" name=\"anno\"  \"></p>");
		sb.append("<input type=\"hidden\" name=\"operazione\" value=\""+op+"\">");
		sb.append("<input type=\"submit\" name =\"submit\" value=\"OK\">" );
                 sb.append("</div>");
		sb.append("<div class=\"row\">");
		sb.append("</form>");
		return sb.toString();	
		}
	
	
	
	
	
	
	
	
	public static String graficiCategorie(ArrayList<Grafici> g,String tipo,String titolo,String unita,String anno,String mese) throws SQLException{
		StringBuilder sb=new StringBuilder();
	
	sb.append("	<script src=\"js/Charts/highcharts.js\"></script>");
	sb.append("	<script src=\"js/Charts/modules/exporting.js\"></script>");	
	sb.append("<script>");
	sb.append("	$(function () {");
	sb.append("	    $('#container').highcharts({");
	sb.append(" title: { text: '"+titolo+"',   }," );
	sb.append("	        chart: {");
	sb.append("      type: '"+tipo+"'");
	sb.append("	        },");
	sb.append("	        xAxis: {");
		if(titolo.equals("temperatura")){
			 sb.append("type: 'datetime',");
		     sb.append("    dateTimeLabelFormats: {");
		     sb.append("       day: '%e of %b'");
	         sb.append("   }");
		}else{
			 sb.append("categories: [");
		int i=0;	
		for(String x:g.get(0).getCategorie()){
			 sb.append("'"+x+"',");
						i++;
			}
		     sb.append("]");
	
		}
	sb.append("	        },");
	
	sb.append(" yAxis: {");
    sb.append("    title: {text: '"+titolo+"("+unita+") '},");
    sb.append("},");
	
	sb.append("	        plotOptions: {");
	sb.append("	            series: {");
	sb.append("	                allowPointSelect: true");
	sb.append("            }");
    sb.append("},");
	sb.append("        series: [");
			
			for(Grafici gra:g){
				sb.append("    {");
			    sb.append("  name: '"+gra.getNome()+"'," );
	    		sb.append("      data:[");
	    		for(int j=0;j<gra.getY().size();j++){
                            if(gra.getY().get(j)!=-9999){
                                 sb.append(""+gra.getY().get(j)+",");
                            }else sb.append("null,");
                           
	    		}
	    		sb.append("],");
	    		if(titolo.equals("temperatura")) {
					 sb.append("pointStart: Date.UTC("+anno+", "+(Integer.parseInt(mese)-1)+", 1),");
		              sb.append("pointInterval: 24 * 3600*1000 , ");
				}
	    		
	    		sb.append("   },");
			}
				  
	 sb.append("],");		      
	 sb.append("    });");	    
	 sb.append("});");
	 sb.append("</script>");
	 sb.append("		<div id=\"container\" style=\"height: 400px\"></div>");
	 return sb.toString();	

	}
	
	public static String graficiMultipliPrecipitazioni(ArrayList<Grafici> g,String tipo,String titolo,String unita,String unita2,String titolo1,String titolo2,String anno,String mese,ArrayList<Dati> d){
            StringBuilder sb=new StringBuilder();
		sb.append(		"		<script src=\"js/Charts/highcharts.js\"></script>" );
		sb.append(		"		<script src=\"js/Charts/modules/exporting.js\"></script>");
		sb.append(		"		<script type=\"text/javascript\">" );
		
		sb.append("$(function () {");
		sb.append(" $('#grafico').highcharts({");
		sb.append(" chart: {");
        sb.append("    zoomType: 'xy'");
        sb.append("},");
        sb.append("title: {");
        sb.append("    text: '"+titolo+"'");
        sb.append(" },");      
        sb.append("xAxis: [{");   
        sb.append("type: 'datetime',");
        sb.append("    dateTimeLabelFormats: {");
        sb.append("       day: '%e of %b'");
        sb.append("   }");
        sb.append(" }],");
        sb.append("yAxis: [{"); 
        sb.append("   labels: {");
        sb.append(  "   format: '{value}"+unita+"',");
        sb.append("     style: {");
        sb.append("         color: Highcharts.getOptions().colors[2]");
        sb.append("    }");
        sb.append("},");
        sb.append("title: {");
        sb.append("    text: '"+titolo1+"',");
        sb.append("  style: {");
        sb.append("       color: Highcharts.getOptions().colors[2]");
        sb.append("   }");
        sb.append("},");
        sb.append("}, {"); 
        sb.append(" gridLineWidth: 0,");
        sb.append(" title: {");
        sb.append("       text: '"+titolo2+"',");
        sb.append("       style: {");
        sb.append("    color: Highcharts.getOptions().colors[0]");
        sb.append(" }");
        sb.append(" },opposite: true,");
        sb.append(" labels: {");
        sb.append("  format: '{value}"+unita2+"',");
        sb.append("      style: {");
        sb.append(" color: Highcharts.getOptions().colors[0]");
        sb.append("   }  }");
        sb.append("},],");
        sb.append(" tooltip: {");
        sb.append("    shared: true");
        sb.append("},");
        sb.append("legend: {");
        sb.append("layout: 'vertical',");
        sb.append("   align: 'left',");
        sb.append("  x: 120,");
        sb.append("  verticalAlign: 'top',");
        sb.append("   y: 80,");
        sb.append(" floating: true,");
        sb.append(" backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'");
        sb.append("},");
        sb.append("series: [");
        
        for(int i=0;i<g.size();i+=2){
        	sb.append("{");
        	
             sb.append("   name: '"+g.get(i).getNome()+"',");
             sb.append("  type: 'column',");
             sb.append(" yAxis: 0,");
             sb.append(" data: [");
               for(int j=0;j<g.get(i).getY().size();j++){
            	   if(g.get(i).getY().get(j)==-9999) sb.append("null,");
                   else sb.append(""+g.get(i).getY().get(j)+",");
               }
            	   
              sb.append("],");
              sb.append("pointStart: Date.UTC("+anno+", "+(Integer.parseInt(mese)-1)+", 1),");
              sb.append("pointInterval: 24 * 3600*1000 , ");
              sb.append("tooltip: {");
              sb.append("    valueSuffix: ' "+unita+"'");
              sb.append("}");   
        	  sb.append("},");
        }
        for(int i=1;i<g.size();i+=2){
        	sb.append("{");        	
            sb.append("   name: '"+g.get(i).getNome()+"',");
            sb.append("  type: 'line',");
            sb.append(" yAxis: 1,");
            sb.append(" data: [");
               for(int j=0;j<g.get(i).getY().size();j++){
                   if(g.get(i).getY().get(j)==-9999) sb.append("null,");
                   else sb.append(""+g.get(i).getY().get(j)+",");
               }
            	   
             sb.append("],");
             sb.append("pointStart: Date.UTC("+anno+", "+(Integer.parseInt(mese)-1)+", 1),");
             sb.append("pointInterval: 24 * 3600*1000 , ");
             sb.append("tooltip: {");
             sb.append("    valueSuffix: ' "+unita2+"'");
             sb.append("}");
        	sb.append("},");
        }
	
        sb.append("]");
        sb.append(" });");
        sb.append("});");
        sb.append("</script>"); 
			sb.append(	"<div id=\"grafico\" style=\"min-width: 100%; height: 100%; margin: 0 auto\"></div> ");
        int i=0;
        for(Dati da:d){
        	if(da.getOk()==false) sb.append("<p>dati in "+da.getAnno()+" non attendibile </p>");     //controllare
        }
		return sb.toString();
	}
	
	public static String graficiMultipli(ArrayList<Grafici> g,String tipo,String titolo,String unita,String unita2,String titolo1,String titolo2){
		StringBuilder sb=new StringBuilder();
		sb.append("<script src=\"http://code.highcharts.com/highcharts.js\"></script>");
		sb.append("<script src=\"http://code.highcharts.com/modules/exporting.js\"></script>");
		sb.append("<script >");
		sb.append("$(function () {");
		sb.append(" $('#container').highcharts({");
		sb.append(" chart: {");
        sb.append("    zoomType: 'xy'");
        sb.append("},");
        sb.append("title: {");
        sb.append("    text: '"+titolo+"'");
        sb.append(" },");
        sb.append("xAxis: [{");
        sb.append("   categories: [");
         
         for(int i=0;i<g.get(0).getCategorie().size();i++){   	 
        	 sb.append("'"+g.get(0).getCategorie().get(i)+"',"); 
         }
        
        sb.append("]  }],");
        sb.append("yAxis: [");
        sb.append(" {"); 
        sb.append(" gridLineWidth: 0,");
        sb.append(" title: {");
        sb.append("       text: '"+titolo2+"',");
        sb.append("       style: {");
        sb.append("    color: 'black'");
        sb.append(" }");
        sb.append(" },");        
        sb.append(" labels: {");
        sb.append("  format: '{value}"+unita2+"',");
        sb.append("      style: {");
        sb.append(" color: 'black'");
        sb.append("   }  }");
        sb.append("},");
        sb.append("{"); 
        sb.append("opposite: true,");
        sb.append("   labels: {");
        sb.append(  "   format: '{value}"+unita+"',");
        sb.append("     style: {");
        sb.append("         color: 'black'");
        sb.append("    }");
        sb.append("},");
        sb.append("title: {");
        sb.append("    text: '"+titolo1+"',");
        sb.append("  style: {");
        sb.append("       color: 'black'");
        sb.append("   }");
        sb.append("},");
        sb.append("},"); 
        sb.append("],");
        sb.append(" tooltip: {");
        sb.append("    shared: true");
        sb.append("},");
        sb.append("legend: {");
        /*sb.append("layout: 'vertical',");
        sb.append("   align: 'left',");
        sb.append("  x: 0,");
        sb.append("  verticalAlign: 'top',");
        sb.append("   y: 100,");
        sb.append(" floating: true,");
        sb.append(" backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'");*/
        
        sb.append("legend: {" );
        		sb.append(	"		            layout: 'vertical'," );
        				sb.append(	"		            align: 'right'," );
        						sb.append(	"		            verticalAlign: 'middle'," );
        								sb.append(    "borderColor: '#C98657',");
        										sb.append(  " borderWidth: 10,");
           
        sb.append(	"		        }");       
        sb.append("},");
        sb.append("series: [");
        // precipitazioni
        sb.append("{");
        sb.append("   name: 'precipitazioni',");
        sb.append("  type: 'column',");
        sb.append("color: 'grey',");   
        sb.append(" yAxis: 1,");
        sb.append(" data: [");
        for(int j=0;j<g.get(0).getY().size();j++){
            	   sb.append(""+g.get(0).getY().get(j)+",");
         }
            	   
        sb.append("],");
        sb.append("tooltip: {");
        sb.append("    valueSuffix: ' "+unita+"'");
        sb.append("}");
        sb.append("},");
        
       //avg
        sb.append("{");        	
        sb.append("   name: 'avg',");
        sb.append("  type: 'line',");
        sb.append(" yAxis: 0,");
        sb.append("color: 'black',");
        sb.append(" data: [");
        for(int j=0;j<g.get(1).getY().size();j++){
            	   sb.append(""+g.get(1).getY().get(j)+",");
         }
            	   
        sb.append("],");
        sb.append("tooltip: {");
        sb.append("    valueSuffix: ' "+unita2+"'");
        sb.append("}");
        sb.append("},");
       //min
        sb.append("{");
        sb.append("   name: 'min',");
        sb.append("  type: 'line',");
        sb.append(" yAxis: 0,");
        sb.append("color: 'blue',");
        sb.append(" data: [");
        for(int j=0;j<g.get(2).getY().size();j++){
           	   sb.append(""+g.get(2).getY().get(j)+",");
        }
           	   
        sb.append("],");
        sb.append("tooltip: {");
        sb.append("    valueSuffix: ' "+unita2+"'");
        sb.append("}");    
       	sb.append("},");       	
       	//max
       	sb.append("{");
        sb.append("   name: 'Max',");
        sb.append("  type: 'line',");
        sb.append(" yAxis: 0,");
        sb.append("color: 'red',");
        sb.append(" data: [");
        for(int j=0;j<g.get(3).getY().size();j++){
       	   sb.append(""+g.get(3).getY().get(j)+",");
        }       	   
        sb.append("],");
        sb.append("tooltip: {");
        sb.append("    valueSuffix: ' "+unita2+"'");
        sb.append("}");      
        sb.append("},");
   	//maxmax
        sb.append("{");	
        sb.append("   name: 'maxMax',");
        sb.append("  type: 'line',");
        sb.append("lineWidth : 0,");
        sb.append(" yAxis: 0,");
        sb.append("color: 'red',");
        sb.append(" data: [");
        for(int j=0;j<g.get(4).getY().size();j++){
   	   		sb.append(""+g.get(4).getY().get(j)+",");
        }
   	   
        sb.append("],");
        sb.append("tooltip: {");
        sb.append("    valueSuffix: ' "+unita2+"'");
        sb.append("}");
        sb.append("},");
	
	//minmin
        sb.append("{");	
        sb.append("   name: 'minMin',");
        sb.append("  type: 'line',");
        sb.append("lineWidth : 0,");
        sb.append(" yAxis: 0,");
        sb.append("color: 'blue',");
        sb.append(" data: [");
      for(int j=0;j<g.get(5).getY().size();j++){
    	  sb.append(""+g.get(5).getY().get(j)+",");
      }
   	   
        sb.append("],");
        sb.append("tooltip: {");
        sb.append("    valueSuffix: ' "+unita2+"'");
      	sb.append("}");  
      	sb.append("},");
      	sb.append("]");
      	sb.append(" });");
      	sb.append("});");
      	sb.append("</script>"); 
      	sb.append("<div id=\"container\" style=\"min-width: 300px; height: 400px; margin: 0 auto\"></div>");
		return sb.toString();
	}


	public static String listaElaborazioni(){
		StringBuilder sb = new StringBuilder();

		sb.append("	<div class=\"row\">");
		sb.append("	<div class=\"col-xs-6 col-md-11 col-md-push-1\"><h3>Query sui Dati Climatici</h3></div>");
		sb.append("	</div>");
		sb.append("	<br>");
		sb.append("	<div class=\"list-group\">");
		sb.append("	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=scegliStazioniDeltaT\" class=\"list-group-item\"> Distribuzione Delta T</a></div>");
		sb.append("  	</div>	");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=scegliStazioniT\" class=\"list-group-item\">Distribuzione Temperature</a></div>");
		sb.append("  	</div>");
		sb.append("  	<div class=\"row\">");
		sb.append("  	<div class=\"col-xs-6 col-md-4  col-md-push-1\"><a href=\"Servlet?operazione=scegliStazioniPrecipitazioni\" class=\"list-group-item\"> Distribuzione precipitazioni</a></div>");
		sb.append("  	</div>");
		sb.append("  	</div>");
		return sb.toString();
	}

}
