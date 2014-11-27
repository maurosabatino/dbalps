<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica.*"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.*"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html >
 
<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<jsp:setProperty  name="HTMLc" property="*"/>

<html>

<head>
   <jsp:include page="import.jsp"></jsp:include>

    <script type="text/javascript">
	$(function() {
		$("table").tablesorter({
                            debug: true,
                            sortList: [[0,0]]
                            });
                        
                
	});
	</script>
        <script>
        function elimina(arg) {
            var domanda = confirm("Sei sicuro di voler cancellare?");
            if (domanda === true) {
     
        $.ajax({            
            url: 'Servlet',
            type: 'POST',   
            data: {operazione: 'eliminaStazione', idstazione:arg},
            success: function () {
               window.location.reload();
            }
        });}}
    </script>
       
   
    
<title>Visualizza stazioni</title>
</head>
<body>
  <div class ="container">
<jsp:include page="header.jsp"></jsp:include>
   <div class ="content">
       <div class="row">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-8">
         <table class="table tablesorter" >
         <thead>
             <%Utente part = (Utente) session.getAttribute("partecipante");
             ArrayList<StazioneMetereologica> stazione = (ArrayList<StazioneMetereologica>) request.getAttribute("stazione");%>
             
             <tr> <th>Nome </th><th>Comune </th> <th>Quota </th> <th> Dettagli</th> 
                 <%if(part!=null){
                    if( part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO)){
                    %>
                 <th> Modifica</th>   <th> Elimina</th> </tr>
             <%}}%>
	</thead>
       
        <tbody>
        <% 
        for(StazioneMetereologica s: stazione){ %>
        
            <tr> 
                <td><%=s.getNome()%></td>  <td><%= s.getUbicazione().getLocAmm().getComune() %> </td><td><%= s.getUbicazione().getQuota() %> </td>
                <td> <a href="Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>" role="button"><img alt="Brand" class="img-responsive" src="img/search-icon (32).png"></a></td>
                
                <% if(part!=null &&( part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO)||(part.getRuolo().equals(Role.BASE) && s.getIdUtente()==part.getIdUtente()) )){%>
                        <td> <a href="Servlet?operazione=modificaStazione&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>" role="button"><img alt="Brand" class="img-responsive" src="img/edit-validated-icon.png"></a></td>
                        <td> <a  id="buttonElimina" onclick="elimina(<%=s.getIdStazioneMetereologica()%>);" role="button">
                        <img alt="Brand" class="img-responsive" src="img/delete-icon.png">
                        </a></td>
                 <%}%> 
                
           </tr>
            <%}%>              
	</tbody>
     </table>
        <div class="col-md-offset-2">
        <div id="pager" class="pager">
		<img src="img/first.png" class="first"/>
		<img src="img/prev.png" class="prev"/>
                
		<input type="text" class="pagedisplay"/>
		<img src="img/next.png" class="next"/>
		<img src="img/last.png" class="last"/>
		<select class="pagesize">
			<option selected="selected"  value="10">10</option>
			<option value="30">25</option>
			<option  value="40">50</option>
		</select>
	
        </div>
        </div>
      </div>
    </div>
    
 
</div>
    <jsp:include page="footer.jsp"></jsp:include>
  </div>

</body>
</html>