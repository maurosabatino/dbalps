
<!DOCTYPE html>
<html>
  <head>
    <title>Visualizza Processo</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css"/>

    
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

<script src="../js/jquery-2.1.1.min.js"></script>
   
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDxQdKCfe6zedCyb4DOxEQU2J2KKLZ95oc"></script>
    
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
      map.setMapTypeId(google.maps.MapTypeId.HYBRID);
      };
      google.maps.event.addDomListener(window, 'load', initialize);
     });
    </script>
  </head>
  <body>
  



<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
  
 <div class="container-fluid">
  <div class="row">
    <div class="row">
      <div class="col-md-9 col-md-push-2"><h1> Ghiacciao di Prè de Bar </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>Processo</h1> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-2"><h1>1 mar 1893 <small> 0.57.00</small></h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>Data </h1> </div>
    </div>
  </div>
    <div class="row">
  <h2>Descrizione</h2>
  <p>A "process description" is defined as a general description of how a process happens, step by step.  
    It does not tell the reader what to do; rather, it describes how something happens.  
    Examples of process descriptions are any kind of manual, pamphlet, or sheet describing how a type of machine works, 
    how a human process works, or how a type of event works.  Process descriptions avoid giving commands, avoid using the pronoun "you," 
    and make use of the present tense (as in "First, the driver inserts the key in the lock") and avoid giving commands.
  </p>
</div>
  </div>




<div class="container-fluid">
 
  <div class="col-md-5">
  <div class="row">
    <h2>Ubicazione</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>Cantagallo</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Comune</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>Torino</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Provincia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>Piemonte</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Regione</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>Italia</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Stato</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Sottopo</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Sottobacino</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Po</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Bacino</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Po</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Quota</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Po</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Esposizione</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Po</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Latitudine</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Po</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Longitudine</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Po</p> </div>
      <div class="col-md-4 col-md-pull-6"><strong>Affidabilità Coordinate</strong> </div>
    </div>    
  </div>
    
  <div class="row">
    <h2>Dettagli Processo</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>35.000 m</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Altezza</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>225.000 m</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Larghezza</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>850.000 m<sup>2</sup></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Superficie</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>200.000 m<sup>3</sup></p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Volume</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>3</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Classe Volume</strong> </div>
    </div>
  </div>


  <div class="row">
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p> Frana</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Tipologia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p> Versante</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Sito</strong> </div>
    </div>
  </div>
   
  <div class="row">
    <h2>Litologia</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p>Frana</p></div>
      <div class="col-md-3 col-md-pull-9"><strong>Litologia</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p> Versante</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Proprietà Termiche</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p> Versante</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Stato Fratturazione</strong> </div>
    </div>
  </div>

  <div class="row">
    <h2>Danni</h2>
    <div class="row">
      <div class="col-md-9 col-md-push-4"><p> Edificio Pubblico, Area Industriale</p> </div>
      <div class="col-md-3 col-md-pull-9"><strong>Danni</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>Bubboni, Rotture Multiple, Salti Quantistici</p> </div>
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
    <ol>
  
     <div class="col-md-12">
      <div class="row" >
     <div class="col-md-6 col-md-push-4"><p >veduta aerea</p> </div>
     <div class="col-md-5 col-md-pull-6"><li><strong>Nome</strong></li></div>
    </div>
     
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>23-10-2014</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Data</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>IRPI</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Fonte</strong> </div>
    </div>
     </div>
     
    
    </ol>
    </div>
    
    <div class="col-md-7"> 
    <h3>PDF</h3>
    <ol>
  
     <div class="col-md-12">
      <div class="row" >
     <div class="col-md-6 col-md-push-4"><p >veduta aerea</p> </div>
     <div class="col-md-5 col-md-pull-6"><li><strong>Nome</strong></li></div>
    </div>
     
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>23-10-2014</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Data</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>IRPI</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Fonte</strong> </div>
    </div>
     </div>
     
    
    </ol>
    </div>
    <div class="col-md-5"> 
    <h3>Link</h3>
    <ol>
  
     <div class="col-md-12">
      <div class="row" >
        <div class="col-md-6 col-md-push-4"><a href="#"> veduta aerea</a></div>
     <div class="col-md-5 col-md-pull-6"><li><strong>Nome</strong></li></div>
    </div>
     
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>23-10-2014</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Data</strong> </div>
    </div>
    <div class="row">
      <div class="col-md-6 col-md-push-4"><p>IRPI</p> </div>
      <div class="col-md-5 col-md-pull-6"><strong>Fonte</strong> </div>
    </div>
     </div>
     
    
    </ol>
    </div>
    
  </div>

 

</div> <!--fine parte centrale-->



 </body>
</html>