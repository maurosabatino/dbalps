<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
 
 
<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.entity.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<jsp:useBean id="stazione" class="it.cnr.to.geoclimalp.dbalps.entity.stazione.StazioneMetereologica" scope="request" />
<jsp:setProperty  name="stazione" property="*"/>
<jsp:useBean id="ubicazione" class="it.cnr.to.geoclimalp.dbalps.entity.ubicazione.Ubicazione" scope="request" />
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
        <div class="col-md-9 col-md-push-2"><h1> <%=stazione.getNome()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>Dettagli stazione meteorologica</h1> </div>
    </div>
  </div>
      <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getAggregazioneGiornaliera() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>aggregazione temporale</strong> </div>
    </div>
      <div class="row">
      <div class="col-md-9 col-md-push-4"><p><!-- da fare--></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Tipo aggregazione giornaliera</strong> </div>
    </div>
      <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getDataInizio() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Data inizio attività</strong> </div>
    </div>
      <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getDataFine() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Data fine attività</strong> </div>
    </div>
       <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getEnte() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Ente</strong> </div>
    </div>
  </div>




<div class="container-fluid">
 
  <div class="col-md-5">
  <div class="row">
    <h2>Ubicazione</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getComune() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Comune</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getProvincia() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Provincia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getRegione() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Regione</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getRegione() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Stato</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getLocIdro().getSottobacino() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Sottobacino</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getLocIdro().getBacino() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Bacino</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getQuota() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Quota</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getEsposizione() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Esposizione</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getCoordinate().getX() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Latitudine</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getCoordinate().getY() %></p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Longitudine</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getAttendibilita() %></p> </div>
      <div class="col-md-4 col-md-pull-6"><strong>Affidabilità Coordinate</strong> </div>
    </div>   
      <div class="row">
      <div class="col-md-9 col-md-push-4"><p> <%=stazione.getSito().getCaratteristiche_IT() %></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Morfologia sito</strong> </div>
    </div>
  </div>
    
  <div class="row">
    <h2>Sensori</h2>
        <div class="row">
        <div class="col-md-9 col-md-push-4"><p>
                <%     StringBuilder sensori=new StringBuilder();
                sensori.append(stazione.getSensori().get(0).getSensori_IT() );
                for(int i=1;i<stazione.getSensori().size();i++){
                    sensori.append(","+stazione.getSensori().get(i).getSensori_IT() );
                }
         
                %>
                  <%=sensori.toString() %>            </p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Danni</strong> </div>
    </div>
   <div class="row">
  <h2>Note</h2>
  <p><%=stazione.getNote() %>
  </p>
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
       for( i=0;i<stazione.getAllegati().size();i++){
            
        if(stazione.getAllegati().get(i).getTipoAllegato().equals("image")){%>
             <ol>
             <div class="col-md-12">
              <div class="row" >
          
              <div class="col-md-6 col-md-push-4"><p ></p> </div>
              <div class="col-md-5 col-md-pull-6"><li><strong><%=stazione.getAllegati().get(i).getTitolo()%></strong></li></div>
             </div>
             <div class="row">
             <div class="col-md-6 col-md-push-4"><p><%=stazione.getAllegati().get(i).getLinkFile()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=stazione.getAllegati().get(i).getAnno()%></strong> </div>
             </div>
             <div class="row">
              <div class="col-md-6 col-md-push-4"><p><%=stazione.getAllegati().get(i).getAutore()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=stazione.getAllegati().get(i).getFonte()%></strong> </div>
             </div>
           </div>
            </ol>
        <%} } %>
    </div>
    
    <div class="col-md-7"> 
    <h3>PDF</h3>
   <%   i=0;
       for( i=0;i<stazione.getAllegati().size();i++){
            
        if(stazione.getAllegati().get(i).getTipoAllegato().equals("document")){%>
             <ol>
             <div class="col-md-12">
              <div class="row" >
          
              <div class="col-md-6 col-md-push-4"><p ></p> </div>
              <div class="col-md-5 col-md-pull-6"><li><strong><%=stazione.getAllegati().get(i).getTitolo()%></strong></li></div>
             </div>
             <div class="row">
                 <div class="col-md-6 col-md-push-4"><a href="C:\Users\daler\Desktop\dario" > link </a> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=stazione.getAllegati().get(i).getAnno()%></strong> </div>
             </div>
             <div class="row">
              <div class="col-md-6 col-md-push-4"><p><%=stazione.getAllegati().get(i).getAutore()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=stazione.getAllegati().get(i).getFonte()%></strong> </div>
             </div>
           </div>
            </ol>
        <%} } %>

    </div>
    <div class="col-md-5"> 
    <h3>Link</h3>
    <%   i=0;
       for( i=0;i<stazione.getAllegati().size();i++){
            
        if(stazione.getAllegati().get(i).getTipoAllegato().equals("link")){%>
             <ol>
             <div class="col-md-12">
              <div class="row" >
          
              <div class="col-md-6 col-md-push-4"><p ></p> </div>
              <div class="col-md-5 col-md-pull-6"><li><strong><%=stazione.getAllegati().get(i).getTitolo()%></strong></li></div>
             </div>
             <div class="row">
                 <div class="col-md-6 col-md-push-4"><a href="C:\Users\daler\Desktop\dario" > link </a> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=stazione.getAllegati().get(i).getAnno()%></strong> </div>
             </div>
             <div class="row">
              <div class="col-md-6 col-md-push-4"><p><%=stazione.getAllegati().get(i).getAutore()%></p> </div>
              <div class="col-md-5 col-md-pull-6"><strong><%=stazione.getAllegati().get(i).getFonte()%></strong> </div>
             </div>
           </div>
            </ol>
        <%} } %>
    </div>
    
  </div>
      </div>
    </div>
    
 
</div>
    <jsp:include page="footer.jsp"></jsp:include>
  </div>
</div>
  </div>
</body>
</html>