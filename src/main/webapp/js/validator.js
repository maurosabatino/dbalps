
$(document).ready(function () {
var d = new Date();
  $("#insertProcesso").bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            nome: {
               validators: {
                    notEmpty: {
                        message: 'The name is required'
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
        longitudine :{
            	 validators: {
                    between: {
                        min: -180,
                        max: 180,
                        message: 'The longitude must be between -180.0 and 180.0'
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
            
        }
        
        
      
    });


});

