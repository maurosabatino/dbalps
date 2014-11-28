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
   <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
            <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
            <link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap.css"/>
            <link rel="stylesheet" type="text/css" href="css/layout.css"/>


            <!--JAVASCRIPT-->


           <script src ="js/jquery-1.11.1.min.js"></script>
           <script src="js/bootstrap.js"></script>
            <script src ="js/jquery.dataTables.min.js"></script>
            <script src ="js/dataTables.bootstrap.js"></script> 
       

            <script>
                $(document).ready(function () {
                    
                    $('table').dataTable({
                        "language": {
                        "lengthMenu": "Display _MENU_ process per page",
                        "zeroRecords": "Nothing found - sorry",
                        "info": "Showing page _PAGE_ of _PAGES_",
                        "infoEmpty": "No process available",
                        "infoFiltered": "(filtered from _MAX_ total process)"
        }
                    });
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
                        <td><%=u.getNome()%> </td>
                        <td> <%=u.getCognome()%></td>
			<td><%=u.getUsername()%></td> 
			<td><%=u.getRuolo()%></td>
			<td><%=u.getAttivo()%></td>
                        <td> <a href="Servlet?operazione=mostraUtente&user=<%=u.getUsername()%>">Dettagli</a> </td>
			</tr>
                        <%}%>
		</tbody>
     </table>
        
     <% }else{%>
        
     <% } %>
     </div>
    </div>
    
 

    <jsp:include page="footer.jsp"></jsp:include>
  </div>
</body>
</html>