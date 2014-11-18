<%@page import="bean.Utente.Utente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NavBar</title>
</head>
<body>

 
    <div class="menu">
     <a class="navbar-brand" href="Servlet?operazione=changeLanguage&language=it-IT">
      <img alt="Brand" src="img/italy_round_icon_64.png">
      </a>
    <a class="navbar-brand" href="Servlet?operazione=changeLanguage&language=en-US">
      <img alt="Brand" src="img/united_kingdom_round_icon_64.png">
      </a>
    
    
   
  
  <div class="navbar-collapse collapse">
    
 <% Utente part = (Utente)session.getAttribute("partecipante");
	if (part==null){
%>	
<ul class="nav navbar-nav navbar-right">

<li><button class="btn btn-info navbar-btn " data-toggle="modal" data-target=".login-form">Login</button></li>

</ul>

  <%}else{ %>
  <form  class="navbar-form navbar-right" action="Servlet" name="dati" method="POST">
  <div class="form-group">
  
<div class="row">
  <div class="col-md-6">
  <input type=hidden name=operazione value=logout>
Utente: <%=part.getUsername()%><button type="submit" class="btn btn-warning navbar-btn btn-sm">Logout</button>
  </div>
</div>
</div>
</form>
<% }%>

  </div>
</div>
   
<div class="modal login-form" tabindex="-6" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  				<div class="modal-dialog modal-sm">
    				<div class="modal-content">
     					<form action="Servlet" name="dati" method="POST">
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
							<button type="submit" class="btn btn-default">Login</button>
							</div>
							</div>
							<br>
							<br>
 						</form>
    				</div>
  				</div>
  </div>

 <script>
      
  $(document).ready(function() {
  var num = 50; //number of pixels before modifying styles

$(window).bind('scroll', function () {
    if ($(window).scrollTop() > num) {
        $('.navbar').addClass('navbar-fixed-top');

    } else {
        $('.navbar').removeClass('navbar-fixed-top');
        
    }
});
});

    </script>
</body>
</html>