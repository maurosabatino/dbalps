

<!DOCTYPE html>




<html>

    <head>
        <!--CSS-->
         <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/layout.css"/>
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>
        
              <script src="js/jquery-2.1.1.js"></script>

        <script src="js/jquery-ui.js"></script>
        <script src="js/globalize.js"></script>
        <script src="js/globalize.culture.de-DE.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.sticky-kit.min.js"></script>
        <script src="js/jquery.stickyfooter.min.js"></script>
        <script src="js/selectize.js"></script>
        <script src="js/json.js"></script>
        <script src="js/personalLibrary.js"></script>

        <!--Google Maps-->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
  
        <title>Elaborazioni</title>
    </head>
    <body>
        <div class="container">
            


<div class="row">
    <div class="col-md-10 col-md-push-1">
        <div class ="header">
            <div class="navbar-default">
                
                <img class="img-responsive" src="img/logo_ita.png">
                
            </div>
        </div>
    </div>
</div>


                <div class="row"> 
                




       
            <div class="col-md-2 col-md-offset-1" id=login>

                <ul class="nav nav-sidebar">
                    <li>
                        <a><img class="" alt="HOME" src="img/home-icon .png"  id="home"></a>
                    </li>
                </ul>
                <ul class="nav nav-sidebar">
                    <li>
                        <a><img class="img-circle" id="buttonIT" alt="IT" src="img/italy-icon.png"> 
                        
                        <img class = "img-circle" id="buttonENG" alt="ENG" src="img/uk-icon.png"></a>
                    </li>
                </ul>  
                

                <ul class="nav nav-sidebar">
                    	
                    <li><button class="btn btn-info navbar-btn " data-toggle="modal" data-target=".login-form">Login</button></li>
                    
                </ul>
                
                <ul class="nav nav-sidebar">
                    <li class="dropdown">
                        
                        <a href="#" class="dropdown-toggle disabled" data-toggle="dropdown">Inserisci<b class="caret"></b></a>
                            
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=formInserisciProcesso">Inserisci processo</a></li>
                            <li><a href="Servlet?operazione=formInserisciStazione">Inserisci stazione</a></li>
                            <li><a href="Servlet?operazione=elencocaricaDatiClimatici">Inserisci dati climatici</a></li>
                            <li><a href="Servlet?operazione=scegliProcessoAllegato">Insersici allegato a un processo</a></li>
                            <li><a href="Servlet?operazione=scegliStazioneAllegato">Insersici allegato a un processo</a></li>

                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Mostra<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=mostraTuttiProcessiModifica">Elenca tutti i processi</a></li>
                            <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche">Elenca tutte le stazioni</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Ricerca <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=queryProcesso">Ricerca processo</a></li>
                            <li><a href="Servlet?operazione=queryStazione">Ricerca stazione</a></li>
                            <li><a href="Servlet?operazione=queryClimatiche"> Ricerca dati metereologici</a></li>
                            <li><a href="Servlet?operazione=listaElaborazioni">Elaborazioni</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        
                        <a href="#" class="dropdown-toggle disabled" data-toggle="dropdown">Area riservata<b class="caret"></b></a>
                            
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=formCreaUtente">Crea utente</a></li>
                            <li><a href="Servlet?operazione=visualizzaTuttiUtenti">Visuaizza tutti gli utenti</a></li>
                            <li><a href="Servlet?operazione=ricaricaJson">Aggiorna menù</a></li>
                        </ul>
                    </li>


                </ul>
                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#"> Documentazione </a></li>
                    <li><a href="#">Info</a></li>
                </ul>

                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#">Applicazione Android</a></li>
                </ul>

                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#">Statistiche</a></li>
                </ul>
            </div>
                        
        <div class="modal login-form" tabindex="-6" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form action="Servlet" id="login"  name="dati" method="POST">
                        <div class="form-group">
                            <br>
                            <br>
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1">
                                    <label for="username">Username </label><input type="text" name="username" id="username" class="form-control" placeholder="username"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1">
                                    <label for="password">Password </label><input type="password" name="password" id = "password" class="form-control" placeholder="password"/>
                                    <input type="hidden" name="operazione" value="login"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10 col-md-offset-4">
                                <button id="loginButton" type="submit" class="btn btn-default">Login</button>
                            </div>
                        </div>
                        <br>
                        <br>
                    </form>
                </div>
            </div>
        </div>


                    <div class="col-sm-10 col-md-8">
                    <script src="http://code.highcharts.com/highcharts.js"></script>
                    <script src="http://code.highcharts.com/modules/exporting.js"></script>
                    <script >$(function () { $('#container').highcharts({ chart: {    zoomType: 'xy'},
                                title: {    text: 'precipitazioni e temperatura' },
                                xAxis: [{   categories: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec',]  }],
                                yAxis: [ { gridLineWidth: 0, title: {       text: 'temperatura',       style: {    color: 'black' } },
                                labels: {  format: '{value}C',      style: { color: 'black'   }  }},{opposite: true,   labels: {   format: '{value}mm', 
                                    style: {         color: 'black'    }},title: {    text: 'precipitazione', 
                                    style: {       color: 'black'   }},},], tooltip: {    shared: true},legend: {
                            legend: {		            layout: 'vertical',		            align: 'right',		            verticalAlign: 'middle',borderColor: '#C98657', borderWidth: 10,		        }},
                                series: [{   name: 'precipitazioni',  type: 'column',color: 'grey', yAxis: 1, data: [],
                                tooltip: {    valueSuffix: ' mm'}},{   name: 'avg',  type: 'line', yAxis: 0,color: 'black', 
                                data: [-3.369354838709679,-2.7999999999999994,-2.027419354838709,0.5866666666666667,4.719354838709678,5.875000000000002,7.120967741935484,7.382258064516129,4.735,2.556451612903226,1.611666666666668,-2.1933333333333334,],
                                tooltip: {    valueSuffix: ' C'}},
                            {   name: 'min',  type: 'line', yAxis: 0,color: 'blue',data:[-10.332258064516125,-9.714285714285714,-7.600000000000001,-3.1299999999999986,0.23870967741935487,4.996666666666667,8.14193548387097,6.193548387096775,2.6166666666666667,-0.9096774193548387,-4.96,-10.583870967741936,],tooltip: {    valueSuffix: ' C'}},
                            {   name: 'Max',  type: 'line', yAxis: 0,color: 'red', data: [-4.512903225806451,-4.042857142857143,-1.651612903225806,3.5900000000000003,5.7774193548387105,12.706666666666669,16.7258064516129,13.277419354838715,9.27,4.603225806451612,0.20333333333333284,-5.396774193548388,],tooltip: {    valueSuffix: ' C'}},
                            {   name: 'maxMax',  type: 'line',lineWidth : 0, yAxis: 0,color: 'red',
                                data: [2.6,1.2,5.2,10.8,15.1,18.5,21.8,20.1,14.2,13.2,13.5,1.6,],tooltip: {    valueSuffix: ' C'}},
                            {   name: 'minMin',  type: 'line',lineWidth : 0, yAxis: 0,color: 'blue',
                                data: [-16.9,-16.7,-19.1,-10.3,-4.8,-0.5,2.4,-1.5,-1.5,-7.2,-15.8,-19.6,],
                                tooltip: {    valueSuffix: ' C'}},] });});</script>
                    <div id="container" style="min-width: 300px; height: 400px; margin: 0 auto"></div>
                </div>
            </div>
        </div>
    </body>
</html>
