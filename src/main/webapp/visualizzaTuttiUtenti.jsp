<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Role"%>
<%@ page language="java" contentType="text/html charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<html>
<head>
    <jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
        <jsp:setProperty  name="locale" property="*"/>
        <jsp:useBean id="partecipante" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="session" />
        <jsp:setProperty  name="partecipante" property="*"/>
  <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>

    <!--JAVASCRIPT-->
    <script src="js/jquery-2.1.1.min.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/globalize.js"></script>
    <script src="js/globalize.culture.de-DE.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/personalLibrary.js"></script>
    <script src="js/bootstrapValidator.min.js"></script>
    <script src="js/jquery.sticky-kit.min.js"></script>
    <script src="js/jquery.stickyfooter.min.js"></script>
    <script type="text/javascript" src="js/jquery-latest.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>
    
    <!--Google Maps-->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
    <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>

      <script type="text/javascript">
	$(function() {
		$("table")
			.tablesorter({debug: true
                            })
			.tablesorterPager({container: $("#pager")});
                        
                
	});
	</script>
    
    
<title>Utente</title>
</head>

<body>
 <div class ="container">
<jsp:include page="header.jsp"></jsp:include>
   <div class ="content">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-8">
     <%if (partecipante.getRuolo().equals(Role.AMMINISTRATORE)){%>

      <table class="table" >
         <thead>
             <tr> <th>Nome</th> <th>Cognome</th>  <th>Username</th> <th> Ruolo</th> <th> Attivo</th> <th> Dettagli</th> </tr>
	</thead>
       
        <tbody> 
             <%
            ArrayList<Utente> utenti = (ArrayList<Utente>) request.getAttribute("utenti");
             for(Utente u: utenti ){ %>
			<tr>
                        <td><%=u.getNome()%> </td> <td> <%=u.getCognome()%></td>
			<td><%=u.getUsername()%></td> 
			<td><%=u.getRuolo()%></td>
			<td><%=u.getAttivo()%></td>
                        <td><%=u.getRuolo()%></td>
                        <td> <a href="Servlet?operazione=mostraUtente&user=<%=u.getUsername()%>">Dettagli</a> </td>
			</tr>
                        <%}%>
		</tbody>
     </table>
        <div id="pager" class="pager">
	
		<img src="img/first.png" class="first"/>
		<img src="img/prev.png" class="prev"/>
		<input type="text" class="pagedisplay"/>
		<img src="img/next.png" class="next"/>
		<img src="img/last.png" class="last"/>
		<select class="pagesize">
			<option selected="selected"  value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option  value="40">40</option>
		</select>
	
        </div>
     <% }else{%>
        
     <% } %>
     </div>
    </div>
    
 

    <jsp:include page="footer.jsp"></jsp:include>
  </div>
</body>
</html>