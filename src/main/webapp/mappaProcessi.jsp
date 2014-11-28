<%-- 
    Document   : processiMaps
    Created on : 21-nov-2014, 9.50.42
    Author     : Mauro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--CSS-->
       <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/layout.css"/>

        <style>
            .google-maps {
                position: relative;
                padding-bottom: 100%; // This is the aspect ratio
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

       <script src ="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.js"></script>

        <!--Google Maps-->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>dbalps</title>
    </head>
    <body>
        <div class="container">
            <jsp:include page="header.jsp"></jsp:include>

                <div class="row"> 
                <jsp:include page="barraLaterale.jsp"></jsp:include>
                    <div class="col-md-8">
                        <div class="google-maps">
                            <div class="map-canvas" id="gmap">

                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                <jsp:include page="footer.jsp"></jsp:include>
            </div>

        </div>

        <script>
            $(document).ready(function () {
                var map_center = new google.maps.LatLng(0.1700235000, 20.7319823000);
                var map = new google.maps.Map(document.getElementById("gmap"), {
                    zoom: 2,
                    center: map_center,
                    mapTypeId: google.maps.MapTypeId.HYBRID});

                var pos;
                var marker;
                var marker_list = [];

                var processi = (function () {
                    var processi = null;
                    $.ajax({
                        async: false,
                        global: false,
                        url: 'servletJson?op=processi',
                        dataType: 'json',
                        success: function (data) {
                            processi = data;
                        }
                    });
                    return processi;
                })();

                $.each(processi, function (index, value) {
                    if (value.ubicazione.coordinate.x != 0 || value.ubicazione.coordinate.y != 0) {
                        pos = new google.maps.LatLng(value.ubicazione.coordinate.x, value.ubicazione.coordinate.y);
                        marker = new google.maps.Marker({
                            position: pos,
                            map: map,
                            title: 'Title',
                            icon: new google.maps.MarkerImage("http://maps.google.com/mapfiles/ms/icons/blue.png")
                        });
                        var infowindow = new google.maps.InfoWindow();
                        google.maps.event.addListener(marker, 'click', (function (marker, index) {
                            return function () {
                                infowindow.setContent("nome: " + value.nome + " <br> comune: " + value.ubicazione.locAmm.comune+"<br> data: "+value.data+"<br> \n\
                                                        tipologia processo: "+value.attributiProcesso.tipologiaProcesso.nome_IT+" <br> <a href=\"Servlet?operazione=mostraProcesso&idProcesso="+value.idProcesso+"\">Report</a>");
                                infowindow.open(map, marker);
                            };
                        })(marker, index));
                        marker_list.push(marker);
                    }
                });

                var markerCluster = new MarkerClusterer(map, marker_list, {
                    gridSize: 40,
                    minimumClusterSize: 4,
                    calculator: function (markers, numStyles) {
                        return {
                            text: markers.length,
                            index: numStyles
                        };
                    }
                });
            });
        </script>

    </body>
</html>
