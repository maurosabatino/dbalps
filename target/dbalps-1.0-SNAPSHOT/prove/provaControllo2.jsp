<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
 <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
 <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/> 
 <link rel="stylesheet" href="css/bootstrapValidator.min.css"/>
  <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css">
 <script src="js/jquery.js"></script>

 <script src="js/jquery-ui.js"></script>
<script src="js/globalize.js"></script>
<script src="js/globalize.culture.de-DE.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.min.js"></script>

<script type="text/javascript" src="js/language/it_IT.js"></script>
<script>
$(document).ready(function() {
    $('.registerForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            nome: {
                //message: 'The username is not valid',
                validators: {
                	notEmpty: {
                       // message: 'The username is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 30,
                        //message: 'The username must be more than 6 and less than 30 characters long'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\\sàèéìíîòóùúÀÈÉÌÍÎÒÓÙÚ\-']+$/,
                       // message: 'The username can only consist of alphabetical, number and underscore'
                    }
                    
                }
            },
            numero:{
            	 validators: {
                     integer:{
                     	
                     },
                 	notEmpty: {
                        // message: 'The username is required and cannot be empty'
                     },
            }
            
        }
            }
    });
});
        </script>
<body>
	<form class="registerForm"> 
		<div class="form-group" >
			<label class="control-label" for="nome">Nome:</label>
            <input class="form-control" id="nome" name="nome" type="text" />
            </div>
         
            <label class="control-label" for="numero">Numero:</label>
            <input class="form-control" id="numero" name="numero" type="text" />
        
		<button type="submit" class="btn btn-primary" name="vai">Submit</button>	
	</form>
</body>
</html>