<%@page import="java.util.ArrayList"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.TipologiaProcesso"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >

<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<jsp:useBean id="processo" class="it.cnr.to.geoclimalp.dbalps.bean.processo.Processo" scope="request" />
<jsp:setProperty  name="processo" property="*"/>
<jsp:useBean id="ubicazione" class="it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione" scope="request" />
<jsp:setProperty  name="ubicazione" property="*"/>
<jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
<jsp:setProperty  name="locale" property="*"/>
<html>

    


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
        <jsp:include page="import.jsp"></jsp:include>
    </head>
    <body>
        <div class ="container">
            <jsp:include page="header.jsp"></jsp:include>

                <div class="row">
                <jsp:include page="barraLaterale.jsp"></jsp:include>
                    <div class="col-md-8  main">



                        <div class="container-fluid">
                            <div class="row">
                                <div class="row">
                                    <div class="col-md-9 col-md-push-3"><h1> <%=processo.getNome()%> </h1></div>
                                <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("dettagliEvento")}</h1> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-9 col-md-push-3"><h1><%= processo.getData()%> <!--<small> 0.57.00</small>--></h1></div>
                                <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("data")} </h1> </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-9 col-md-push-4"><h2>
                                    <%
                                        String tipologia = "";
                                        ArrayList<TipologiaProcesso> tp = processo.getAttributiProcesso().getTipologiaProcesso();
                                        for(int i = 0 ;i < tp.size();i++){
                                            if(locale.getLanguage().equals("it"))
                                                tipologia+=tp.get(i).getNome_IT();
                                            else tipologia+=tp.get(i).getNome_ENG();
                                          if(i+1 < tp.size() && tp.get(i+1)!=null ) tipologia+=",";
                                        }
                                            
                                    %>
                                    <%=tipologia%>
                                </h2> </div>
                                    <div class="col-md-3 col-md-pull-9"><h2><strong>${locale.getWord("tipologia")}</strong></h2> </div>
                        </div>
                        <div class="row">
                            <h2>${locale.getWord("descrizione")}</h2>
                            <p><%=processo.getAttributiProcesso().getDescrizione()%>
                            </p>
                        </div>
                    </div>




                    <div class="container-fluid">

                        <div class="col-md-5">
                            <div class="row">
                                <h2>${locale.getWord("ubicazione")}</h2>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getComune()%></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("comune")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getProvincia()%></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("provincia")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getRegione()%></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("regione")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getUbicazione().getLocAmm().getNazione()%></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("nazione")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getLocIdro().getSottobacino()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("sottobacino")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getLocIdro().getBacino()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("bacino")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getQuota()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("quota")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getEsposizione()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("esposizione")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getCoordinate().getX()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("latitudine")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getCoordinate().getY()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("longitudine")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getUbicazione().getAttendibilita()%></p> </div>
                                    <div class="col-md-4 col-md-pull-6"><strong>${locale.getWord("affidabilitaCoordinate")}</strong> </div>
                                </div>   
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p> <%=processo.getAttributiProcesso().getSitoProcesso().getCaratteristicaSito_IT()%></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("morfologiaSito")}</strong> </div>
                                </div>
                            </div>

                            <div class="row">
                                <h2>${locale.getWord("dettagliEvento")}</h2>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getAltezza()%></p> m</div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("altezzaNicchia")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getLarghezza()%></p> m</div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("larghezzaNicchia")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getSuperficie()%> m<sup>2</sup></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("superficieNicchia")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getVolume_specifico()%> m<sup>3</sup></p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("volumeNicchia")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getClasseVolume().getIntervallo()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("classeVolume")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getRunout()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("runout")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getVolumeAccumulo()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("volumeaccumulo")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p></p><%=processo.getAttributiProcesso().getSuperficieAccumulo()%></div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("superficieaccumulo")}</strong> </div>
                                </div>
                            </div>







                            <div class="row">
                                <h2>${locale.getWord("geologia")}</h2>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p><%=processo.getAttributiProcesso().getLitologia().getNomeLitologia_IT()%></p></div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("litologia")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getProprietaTermiche().getProprieta_termiche_IT()%> </p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("propTermiche")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p> <%=processo.getAttributiProcesso().getStatoFratturazione().getStato_fratturazione_IT()%></p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("statoFratturazione")}</strong> </div>
                                </div>
                            </div>

                            <div class="row">
                                <h2>${locale.getWord("impatti")}</h2>
                                <div class="row">
                                    <div class="col-md-9 col-md-push-4"><p>
                                            <%     StringBuilder danni = new StringBuilder();
                                                if (processo.getAttributiProcesso().getDanni().size() != 0) {
                                                    danni.append(processo.getAttributiProcesso().getDanni().get(0).getTipo_IT());
                                                    for (int i = 1; i < processo.getAttributiProcesso().getDanni().size(); i++) {
                                                        danni.append("," + processo.getAttributiProcesso().getDanni().get(i).getTipo_IT());
                                                    }
                                                }

                                            %>
                                            <%=danni.toString()%>            </p> </div>
                                    <div class="col-md-3 col-md-pull-9"><strong>${locale.getWord("danni")}</strong> </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 col-md-push-4"><p>
                                            <%        StringBuilder effetti = new StringBuilder();
                                                if (processo.getAttributiProcesso().getEffetti().size() != 0) {
                                                    effetti.append(processo.getAttributiProcesso().getEffetti().get(0).getTipo_IT());
                                                    for (int i = 1; i < processo.getAttributiProcesso().getEffetti().size(); i++) {
                                                        danni.append("," + processo.getAttributiProcesso().getEffetti().get(i).getTipo_IT());
                                                    }
                                                }%>
                                            <%=effetti.toString()%>         

                                        </p> </div>
                                    <div class="col-md-5 col-md-pull-6"><strong>${locale.getWord("effettiMorfologici")}</strong> </div>
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
                                for (i = 0; i < processo.getAttributiProcesso().getAllegati().size(); i++) {

                                    if (processo.getAttributiProcesso().getAllegati().get(i).getTipoAllegato().equals("image")) {%>
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
                            <%}
                                } %>
                        </div>

                        <div class="col-md-7"> 
                            <h3>PDF</h3>
                            <%   i = 0;
                                for (i = 0; i < processo.getAttributiProcesso().getAllegati().size(); i++) {

                                    if (processo.getAttributiProcesso().getAllegati().get(i).getTipoAllegato().equals("document")) {%>
                            <ol>
                                <div class="col-md-12">
                                    <div class="row" >
                                        <div class="col-md-6 col-md-push-4"><p ></p> </div>
                                        <div class="col-md-5 col-md-pull-6"><li><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getTitolo()%></strong></li></div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 col-md-push-4"><a href="<%= processo.getAttributiProcesso().getAllegati().get(i).getLinkFile() %>"> link </a> </div>
                                        <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getAnno()%></strong> </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 col-md-push-4"><p><%=processo.getAttributiProcesso().getAllegati().get(i).getAutore()%></p> </div>
                                        <div class="col-md-5 col-md-pull-6"><strong><%=processo.getAttributiProcesso().getAllegati().get(i).getFonte()%></strong> </div>
                                    </div>
                                </div>
                            </ol>
                            <%}
                                } %>

                        </div>
                        <div class="col-md-5"> 
                            <h3>Link</h3>
                            <%   i = 0;
                                for (i = 0; i < processo.getAttributiProcesso().getAllegati().size(); i++) {

                                    if (processo.getAttributiProcesso().getAllegati().get(i).getTipoAllegato().equals("link")) {%>
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
                            <%}
                                }%>
                        </div>
                        <div class="row">
                            <h2>Note</h2>
                            <p><%=processo.getAttributiProcesso().getNote()%>
                            </p>
                        </div>

                    </div>
                    <div class="col-md-5"> 
                        <h3>Map</h3>



                    </div>



                </div> <!--fine parte centrale-->


            </div>


            <br/>
            <jsp:include page="footer.jsp"></jsp:include>
            </div>
            <script>
                $(document).ready(function () {


                    function initialize() {
                        var lati = <%=processo.getUbicazione().getCoordinate().getX()%>;
                        var long = <%=processo.getUbicazione().getCoordinate().getY()%>;
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
                        var contentString = "nome:<%=processo.getNome()%> <br> comune: <%=processo.getUbicazione().getLocAmm().getComune()%> <br> data: <%=processo.getData()%><br><a href=\"Servlet?operazione=mostraProcesso&idProcesso=<%=processo.getIdProcesso()%>\">Report</a>";
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
    </body>
</html>