<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >


<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<jsp:useBean id="stazione" class="it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica" scope="request" />
<jsp:setProperty  name="stazione" property="*"/>
<jsp:useBean id="ubicazione" class="it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione" scope="request" />
<jsp:setProperty  name="ubicazione" property="*"/>
<html>

    <head>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/layout.css"/>
        <script src ="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>


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

         <script>
                $(document).ready(function () {


                    function initialize() {
                        var lati = <%=stazione.getUbicazione().getCoordinate().getX()%>;
                        var long = <%=stazione.getUbicazione().getCoordinate().getY()%>;
                        var coord = new google.maps.LatLng(lati, long);

                        var mapOptions = {
                            zoom: 11,
                            center: coord,
                            panControl: false,
                            zoomControl: false,
                            mapTypeControl: false,
                            scaleControl: false,
                            streetViewControl: false,
                            overviewMapControl: false
                        };
                        var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
                        map.setMapTypeId(google.maps.MapTypeId.HYBRID);

                        var marker = new google.maps.Marker({
                            position: coord,
                            map: map,
                            title: 'stazione',
                            icon: new google.maps.MarkerImage("http://maps.google.com/mapfiles/ms/icons/yellow.png")
                        });
                        
                        var contentString = "nome:<%=stazione.getNome()%> <br> comune: <%=stazione.getUbicazione().getLocAmm().getComune()%>";
                        var infowindow = new google.maps.InfoWindow({
                            content: contentString
                        });
                        google.maps.event.addListener(marker, 'click', function () {
                            infowindow.open(map, marker);
                        });
                    }
                    ;
                    google.maps.event.addDomListener(window, 'load', initialize);
                });
        </script>
        <title>dbalps</title>
    </head>
    <body>
        <div class ="container">
            <jsp:include page="header.jsp"></jsp:include>
                <div class ="content">
                    <div class="row">
                    <jsp:include page="barraLaterale.jsp"></jsp:include>
                        <div class="col-md-8">

                           

                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="row">
                                            <div class="col-md-9 col-md-push-2"><h1> <%=stazione.getNome()%> </h1></div>
                                        <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("dettagliStazione")}</h1> </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p>${stazione.aggregazioneGiornaliera}</p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("aggregazioneTemporale")}</strong> </div>
                                    
                                </div>
                                <div class="row"><a href="Servlet?operazione=mostraDatiClimaticiPrecipitazione&idStazione=<%=stazione.getIdStazioneMetereologica()%>" >${locale.getWord("datiPrecipitazione")}</a>
                                    <a href="Servlet?operazione=mostraDatiClimaticiAvg&idStazione=<%=stazione.getIdStazioneMetereologica()%>" >${locale.getWord("datiAvg")} </a>
                                    <a href="Servlet?operazione=mostraDatiClimaticiMin&idStazione=<%=stazione.getIdStazioneMetereologica()%>"  >${locale.getWord("datiMin")}</a>
                                    <a href="Servlet?operazione=mostraDatiClimaticiMax&idStazione=<%=stazione.getIdStazioneMetereologica()%>"  >${locale.getWord("datiMax")}</a>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p>${stazione.tipoAggregazione}</p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("tipoDiAggregazioneGiornaliera")}</strong> </div>
                                </div>
                                <div class="row">
                                     <%if(stazione.getDataInizio()!=null){%>
                                    <div class="col-md-9 col-md-push-4"><p><%=stazione.getDataInizio()%></p> </div>
                                     <%}else{%>
                                      <div class="col-md-9 col-md-push-4"><p></p> </div> <%}%>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("dataInizio")}</strong> </div>
                                </div>
                                <div class="row">
                                       <%if(stazione.getDataFine()!=null){%>
                                    <div class="col-md-9 col-md-push-4"><p><%=stazione.getDataFine()%></p> </div>
                                    <%}else{%>
                                    <div class="col-md-9 col-md-push-4"><p></p> </div>
                                    <%}%>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("dataFine")}</strong> </div>
                       

                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=stazione.getEnte().getEnte()%></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("ente")}</strong> </div>
                                </div>
                            </div>




                            <div class="container-fluid">

                                <div class="col-md-5">
                                    <div class="row">
                                        <h2>Ubicazione</h2>
                                        <div class="row">
                                            <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getComune()%></p> </div>
                                            <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("comune")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getProvincia()%></p> </div>
                                            <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("provincia")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getRegione()%></p> </div>
                                            <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("regione")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-9 col-md-push-4"><p><%=stazione.getUbicazione().getLocAmm().getNazione()%></p> </div>
                                            <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("nazione")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getLocIdro().getSottobacino()%></p> </div>
                                            <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("sottobacino")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getLocIdro().getBacino()%></p> </div>
                                            <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("bacino")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <%if(stazione.getUbicazione().getQuota()==0.0){%>
                                            <div class="col-md-6 col-md-push-4"><p></p> </div>
                                           <%}else{%>
                                             <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getQuota()%></p> </div>
                                            <%}%>
                                            <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("quota")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getEsposizione()%></p> </div>
                                            <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("esposizione")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getCoordinate().getX()%></p> </div>
                                            <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("latitudine")}</strong> </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6 col-md-push-4"><p><%=stazione.getUbicazione().getCoordinate().getY()%></p> </div>
                                            <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("longitudine")}</strong> </div>
                                        </div>
                                         
                                        <div class="row">
                                            <div class="col-md-9 col-md-push-4"><p> <%=stazione.getSito().getCaratteristiche_IT()%></p> </div>
                                            <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("morfologiaSito")}</strong> </div>
                                        </div>
                                    </div>
                               
                                    <div class="row">
                                        <h2>${locale.getWord("sensori")}</h2>
                                        <div class="row">
                                            <div class="col-md-9 col-md-push-4"><p>
                                                    <%     StringBuilder sensori = new StringBuilder();
                                                        if (stazione.getSensori().size() != 0) {
                                                            sensori.append(stazione.getSensori().get(0).getSensori_IT());
                                                            for (int i = 1; i < stazione.getSensori().size(); i++) {
                                                                sensori.append("," + stazione.getSensori().get(i).getSensori_IT());
                                                            }
                                                        }


                                                    %>
                                                    <%=sensori.toString()%>            </p> </div>
                                            
                                        </div>
                                        <div class="row">
                                            <h2>Note</h2>
                                            <p><%=stazione.getNote()%>
                                            </p>
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

                                    <h2>${locale.getWord("allegati")}</h2>
                                    <div class="col-md-6"> 
                                        <h3>${locale.getWord("immagini")}</h3>
                                        <% int i = 0;
                                        if( stazione.getAllegati().size()!=0){
           for (i = 0; i < stazione.getAllegati().size(); i++) {

               if (stazione.getAllegati().get(i).getTipoAllegato().equals("image")) {%>
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
                                        <%}
            }} %>
                                    </div>

                                    <div class="col-md-7"> 
                                        <h3>PDF</h3>
                                        <%
                                         if( stazione.getAllegati().size()!=0){
                                         i = 0;
       for (i = 0; i < stazione.getAllegati().size(); i++) {

           if (stazione.getAllegati().get(i).getTipoAllegato().equals("document")) {%>
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
                                        <%}
            } }%>

                                    </div>
                                    <div class="col-md-5"> 
                                        <h3>Link</h3>
                                        <%
                                          if( stazione.getAllegati().size()!=0){
                                         i = 0;
        for (i = 0; i < stazione.getAllegati().size(); i++) {

            if (stazione.getAllegati().get(i).getTipoAllegato().equals("link")) {%>
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
                                        <%}
            }}%>
                                    </div>

                                </div>
                            </div>
                        </div>


                    </div>
                    <jsp:include page="footer.jsp"></jsp:include>
                </div>
            

    </body>
</html>