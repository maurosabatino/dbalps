<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
 
<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<jsp:useBean id="processo" class="it.cnr.to.geoclimalp.dbalps.bean.processo.ProcessoCompleto" scope="request" />
<jsp:setProperty  name="processo" property="*"/>
<jsp:useBean id="ubicazione" class="it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione" scope="request" />
<jsp:setProperty  name="ubicazione" property="*"/>
<html>

<head>
    <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>

    
    <style>
    .google-maps {
        position: relative;
        padding-bottom: 133%; // This is the aspect ratio
        height: 0;
        overflow: hidden;
    }
    .google-maps > .map-canvas {
        position: absolute;
        top: 5%;
        left: 0;
        width: 100% !important;
        height: 100% !important;
    }
</style>
    <!--JAVASCRIPT-->
    <script src="js/jquery-2.1.1.min.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/globalize.js"></script>
    <script src="js/globalize.culture.de-DE.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/SeparateDate.js"></script>
    <script src="js/personalLibrary.js"></script>
    <script src="js/bootstrapValidator.min.js"></script>
    <script src="js/jquery.sticky-kit.min.js"></script>
    <script src="js/jquery.stickyfooter.min.js"></script>
    
    <!--Google Maps-->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
    <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
    <script>
      $(document).ready(function(){
        var map;
          function initialize() {
          var mapOptions = {
            zoom: 11,
            center: new google.maps.LatLng(45.912586,7.040834,12),
            panControl: false,
            zoomControl: false,
            mapTypeControl: false,
            scaleControl: false,
            streetViewControl: false,
            overviewMapControl: false
          };
      map = new google.maps.Map(document.getElementById("map-canvas"),mapOptions);
      map.setMapTypeId(google.maps.MapTypeId.SATELLITE);
      };
      google.maps.event.addDomListener(window, 'load', initialize);
     });
    </script>
<title>Evento</title>
</head>
<body>
  <div class ="container">
<jsp:include page="header.jsp"></jsp:include>
   <div class ="content">
       <div class="row">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-9 col-md-offset-3 col-lg-9 col-lg-offset-3 main">
     
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
  
 <div class="container-fluid">
  <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=processo.getNome()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>Dettagli evento</h1> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-2"><h1><%= processo.getData() %> <!--<small> 0.57.00</small>--></h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>Data </h1> </div>
    </div>
  </div>
      <div class="row">
        <div class="col-md-9 col-md-push-4"><p>
               <%     StringBuilder tipologia=new StringBuilder();
                tipologia.append(processo.getAttributiProcesso().getTipologiaProcesso().get(0).getNome_IT());
                for(int i=1;i<processo.getAttributiProcesso().getTipologiaProcesso().size();i++){
                    tipologia.append(","+processo.getAttributiProcesso().getTipologiaProcesso().get(0).getNome_IT() );
                }
         
                %>
                  <%=tipologia.toString() %>
            </p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Tipologia</strong> </div>
    </div>
    <div class="row">
  <h2>Descrizione</h2>
  <p><%=processo.getAttributiProcesso().getDescrizione()%>
  </p>
</div>
  </div>




<div class="container-fluid">
 
  <div class="col-md-5">
  <div class="row">
    <h2>Ubicazione</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getComune() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Comune</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getProvincia() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Provincia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getRegione() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Regione</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getRegione() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Stato</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getLocIdro().getSottobacino() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Sottobacino</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getLocIdro().getBacino() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Bacino</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getQuota() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Quota</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getEsposizione() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Esposizione</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getCoordinate().getX() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Latitudine</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getCoordinate().getY() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Longitudine</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getAttendibilita() %></p> </div>
      <div class="col-md-4 col-md-pull-6"><strong>Affidabilità Coordinate</strong> </div>
    </div>   
      <div class="row">
      <div class="col-md-9 col-md-push-4"><p> <%=processo.getAttributiProcesso().getSitoProcesso().getCaratteristicaSito_IT() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Morfologia sito</strong> </div>
    </div>
  </div>
    
  <div class="row">
    <h2>Dettagli Processo</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getAltezza() %></p> m</div>
      <div class="col-md-3 col-md-pull-9"><strong>Altezza nicchia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getLarghezza() %></p> m</div>
      <div class="col-md-3 col-md-pull-9"><strong>Larghezza nicchia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getSuperficie() %> m<sup>2</sup></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Superficie nicchia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getVolume_specifico() %> m<sup>3</sup></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Volume nicchia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getClasseVolume().getIntervallo() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Classe Volume</strong> </div>
    </div>
       <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=// %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Distanza Propagazione</strong> </div>
    </div>
      <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=// %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Volume dell'accumulo</strong> </div>
    </div>
      <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=// %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Superficie dell'accumulo</strong> </div>
    </div>
  </div>


  
    
    
  
   
  <div class="row">
    <h2>Geologia</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getLitologia().getNomeLitologia_IT() %></p></div>
      <div class="col-md-3 col-md-pull-9"><strong>Litologia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getProprietaTermiche().getProprieta_termiche_IT() %> </p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Proprietà Termiche</strong> </div>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-push-4"><p> <%=processo.getAttributiProcesso().getStatoFratturazione().getStato_fratturazione_IT() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Stato Fratturazione</strong> </div>
    </div>
  </div>

  <div class="row">
    <h2>Impatti</h2>
    <div class="row">
        <div class="col-md-9 col-md-push-4"><p>
                <%     StringBuilder danni=new StringBuilder();
                danni.append(processo.getAttributiProcesso().getDanni().get(0).getTipo_IT() );
                for(int i=1;i<processo.getAttributiProcesso().getDanni().size();i++){
                    danni.append(","+processo.getAttributiProcesso().getDanni().get(i).getTipo_IT() );
                }
         
                %>
                  <%=danni.toString() %>            </p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Danni</strong> </div>
    </div>
    <div class="row">
                <div class="col-md-6 col-md-push-4"><p>
           <%        StringBuilder effetti=new StringBuilder();
                effetti.append(processo.getAttributiProcesso().getEffetti().get(0).getTipo_IT() );
                for(int i=1;i<processo.getAttributiProcesso().getEffetti().size();i++){
                    danni.append(","+processo.getAttributiProcesso().getEffetti().get(i).getTipo_IT() );
                }
         
                %>
                  <%=effetti.toString() %>         
                        
            </p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Effetti Morfologici</strong> </div>
    </div>
  </div>
  
    
  </div>
  
  <div class="col-md-7"> 
  <div class="google-maps">
    <div id="map-canvas" class="map-canvas" ></div>
  </div>
    
  </div>
  
  
  
  

</div> <!--fine parte centrale-->
   <div class="container-fluid">
  
    <h2>Allegati</h2>
    <div class="col-md-6"> 
    <h3>Immagini</h3>
       <% int i=0;
       for( i=0;i<processo.getAttributiProcesso().getAllegati().size();i++){
            
        if(processo.getAttributiProcesso().getAllegati().get(i).getTipoAllegato().equals("image")){%>
             <ol>
             <div class="col-md-12">
              <div class="row" >
          
              <div class="col-md-6 col-md-push-4"><p ></p> </div>
              <div class="col-md-5 col-md-pull-6"><li><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getTitolo()%></strong></li></div>
             </div>
             <div class="row">
             <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getAllegati().get(i).getLinkFile()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getAnno()%></strong> </div>
             </div>
             <div class="row">
              <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getAllegati().get(i).getAutore()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getFonte()%></strong> </div>
             </div>
           </div>
            </ol>
        <%} } %>
    </div>
    
    <div class="col-md-7"> 
    <h3>PDF</h3>
   <%   i=0;
       for( i=0;i<processo.getAttributiProcesso().getAllegati().size();i++){
            
        if(processo.getAttributiProcesso().getAllegati().get(i).getTipoAllegato().equals("document")){%>
             <ol>
             <div class="col-md-12">
              <div class="row" >
          
              <div class="col-md-6 col-md-push-4"><p ></p> </div>
              <div class="col-md-5 col-md-pull-6"><li><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getTitolo()%></strong></li></div>
             </div>
             <div class="row">
                 <div class="col-md-6 col-md-push-4"><a href="C:\Users\daler\Desktop\dario" > link </a> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getAnno()%></strong> </div>
             </div>
             <div class="row">
              <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getAllegati().get(i).getAutore()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getFonte()%></strong> </div>
             </div>
           </div>
            </ol>
        <%} } %>

    </div>
    <div class="col-md-5"> 
    <h3>Link</h3>
    <%   i=0;
       for( i=0;i<processo.getAttributiProcesso().getAllegati().size();i++){
            
        if(processo.getAttributiProcesso().getAllegati().get(i).getTipoAllegato().equals("link")){%>
             <ol>
             <div class="col-md-12">
              <div class="row" >
          
              <div class="col-md-6 col-md-push-4"><p ></p> </div>
              <div class="col-md-5 col-md-pull-6"><li><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getTitolo()%></strong></li></div>
             </div>
             <div class="row">
                 <div class="col-md-6 col-md-push-4"><a href="C:\Users\daler\Desktop\dario" > link </a> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getAnno()%></strong> </div>
             </div>
             <div class="row">
              <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getAllegati().get(i).getAutore()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getFonte()%></strong> </div>
             </div>
           </div>
            </ol>
        <%} } %>
    </div>
    
  </div>
     <div class="col-md-5"> 
    <h3>Map</h3>
    
    
    
  </div>

 

</div> <!--fine parte centrale-->

      </div>
    </div>
    
 
</div>
    <jsp:include page="footer.jsp"></jsp:include>
  </div>

</body>
</html>