<%-- 
    Document   : visualizzaStazione
    Created on : 12-nov-2014, 9.33.31
    Author     : Mauro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="stazione" class="it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica" scope="request" />
<jsp:setProperty  name="stazione" property="*"/>
<jsp:useBean id="ubicazione" class="it.cnr.to.geoclimalp.dbalps.bean.ubicazione.Ubicazione" scope="request" />
<jsp:setProperty  name="ubicazione" property="*"/>
<!DOCTYPE html>
<html>
    <head>
        <!--CSS-->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>

        <!--JAVASCRIPT-->
        <script src="js/jquery-1.11.1.min.js"></script>
        <script src="js/jquery-2.1.1.min.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/jquery.sticky-kit.min.js"></script>
        <script src="js/globalize.js"></script>
        <script src="js/globalize.culture.de-DE.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/SeparateDate.js"></script>
        <!--<script src="js/personalLibrary.js"></script>-->
        <script src="js/bootstrapValidator.min.js"></script>
        <script src="js/jquery.stickyfooter.min.js"></script>

        <!--Google Maps-->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report Stazione</title>
    </head>
    <body>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="row">
                    <div class="col-md-9 col-md-push-2"><h1>${stazione.nome}</h1></div>
                    <div class="col-md-2 col-md-pull-9"><h1>Stazione</h1> </div>
                </div>
                <div class="row">
                    <div class="col-md-9 col-md-push-2"><h1>${stazione.dataInizio}</h1></div>
                    <div class="col-md-2 col-md-pull-9"><h1>Data inizio attività </h1> </div>
                </div>
                <div class="row">
                    <div class="col-md-9 col-md-push-2"><h1>${stazione.dataFine}</h1></div>
                    <div class="col-md-2 col-md-pull-9"><h1>Data fine attività </h1> </div>
                </div>

                <div class="row">
                    <h2>Note</h2>
                    <p>${stazione.note}</p>
                </div>
                <div class="container-fluid">

                    <div class="col-md-5">
                        <div class="row">
                            <h2>Ubicazione</h2>
                            <div class="row">
                                <div class="col-md-9 col-md-push-4"><p>${ubicazione.locAmm.comune}</p> </div>
                                <div class="col-md-3 col-md-pull-9"><strong>Comune</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-9 col-md-push-4"><p>${ubicazione.locAmm.provincia}</p> </div>
                                <div class="col-md-3 col-md-pull-9"><strong>Provincia</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-9 col-md-push-4"><p>${ubicazione.locAmm.regione}</p> </div>
                                <div class="col-md-3 col-md-pull-9"><strong>Regione</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-9 col-md-push-4"><p>${ubicazione.locAmm.nazione}</p> </div>
                                <div class="col-md-3 col-md-pull-9"><strong>Stato</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-md-push-4"><p>${ubicazione.locIdro.sottobacino}</p> </div>
                                <div class="col-md-5 col-md-pull-6"><strong>Sotto bacino</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-md-push-4"><p>${ubicazione.locIdro.bacino}</p> </div>
                                <div class="col-md-5 col-md-pull-6"><strong>Bacino</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-md-push-4"><p>${ubicazione.quota}</p> </div>
                                <div class="col-md-5 col-md-pull-6"><strong>Quota</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-md-push-4"><p>${ubicazione.esposizione}</p> </div>
                                <div class="col-md-5 col-md-pull-6"><strong>Esposizione</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-md-push-4"><p>${ubicazione.coordinate.x}</p> </div>
                                <div class="col-md-5 col-md-pull-6"><strong>Latitudine</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-md-push-4"><p>${ubicazione.coordinate.y}</p> </div>
                                <div class="col-md-5 col-md-pull-6"><strong>Longitudine</strong> </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 col-md-push-4"><p>${ubicazione.attendibilita}</p> </div>
                                <div class="col-md-4 col-md-pull-6"><strong>Affidabilità Coordinate</strong> </div>
                            </div>    
                        </div>

                    </div>
                    <div class="col-md-7"> 
                        <div class="google-maps">
                            <div id="map-canvas" class="map-canvas" ></div>
                        </div>

                    </div>                
                </div>
            </div><!--main-->
        </div> <!--fine-->

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
                var map;
                function initialize() {
                    var mapOptions = {
                        zoom: 11,
                        center: new google.maps.LatLng(${ubicazione.coordinate.x}, ${ubicazione.coordinate.y}),
                        panControl: false,
                        zoomControl: false,
                        mapTypeControl: false,
                        scaleControl: false,
                        streetViewControl: false,
                        overviewMapControl: false
                    };
                    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
                    map.setMapTypeId(google.maps.MapTypeId.HYBRID);
                }
                ;
                google.maps.event.addDomListener(window, 'load', initialize);
            });
        </script>
    </body>
</html>
