/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
        
       
  
  $(".footer").stick_in_parent();
  
  $(".sidebar").stick_in_parent();
  var d = new Date();
  $('.insertProcesso').bootstrapValidator({
        //message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            nome: {
               validators: {
                      notEmpty:{
                        
                      }
                    
                }
            },
            
        anno:{
          validators: {
                    between: {
                        min: 0,
                        max: d.getFullYear(),
                        message: 'The year must be between 0 and '+d.getFullYear()+''
                    }
                }
        },
        ora :{
          validators:{
            regexp:{
              regexp: /^(0[0-9]{1}|1[0-9]{1}|2[0-3]{1}):[0-5]{1}[0-9]{1}$/,
              message: 'the format is hh:mm'
            }
          }
        },
        superficie:{
            	 validators: {
                     numeric :{
                     	separator: ',',
                        message :'the separator is comma'
                     }
            }
            
        },
        larghezza:{
            	 validators: {
                     numeric :{
                     	separator: ',',
                        message :'the separator is comma'
                     }
            }
            
        },
        altezza:{
            	 validators: {
                     numeric :{
                     	separator: ',',
                        message :'the separator is comma'
                     }
            }
        },
        volumespecifico:{
            	 validators: {
                     numeric :{
                     	separator: ',',
                        message :'the separator is comma'
                     }
            }
            
        },
        intervallo :{
            	 validators: {
                     integer:{
                     	
                     }
            }
            
        },
        quota :{
            	validators: {
                     numeric :{
                     	separator: ',',
                        message :'the separator is comma'
                     }
            }
            
        },
        latitudine :{
            	 validators: {
                    between: {
                        min: -90,
                        max: 90,
                        message: 'The latitude must be between -90.0 and 90.0'
                    }
                }
            }
            
        },
        longitudine :{
            	 validators: {
                    between: {
                        min: -180,
                        max: 180,
                        message: 'The latitude must be between -90.0 and 90.0'
                    }
                }
            
       }
        
      
    });
     
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
