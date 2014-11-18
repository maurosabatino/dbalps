/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
        
       
  
  $(".footer").stick_in_parent();
  
  $(".sidebar").stick_in_parent();
  
     
 /*Google Maps*/ 
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
