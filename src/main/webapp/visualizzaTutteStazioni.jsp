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
   <!--CSS-->
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
       
   
    
<title>dbalps</title>
</head>
<body>
  <div class ="container">
<jsp:include page="header.jsp"></jsp:include>
   <div class ="content">
       <div class="row">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-8">
         <table class="table  table-striped table-bordered table-condensed" >
         <thead>
             <%Utente part = (Utente) session.getAttribute("partecipante");
             ArrayList<StazioneMetereologica> stazione = (ArrayList<StazioneMetereologica>) request.getAttribute("stazione");%>
             
             <tr> <th>${locale.getWord("nome")} </th><th>${locale.getWord("comune")} </th> <th>${locale.getWord("quota")} </th> <th> ${locale.getWord("dettagli")}</th> 
                 <%if(part!=null){
                    if( part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO)){
                    %>
                 <th> ${locale.getWord("modifica")}</th>   <th> ${locale.getWord("elimina")}</th> <th> ${locale.getWord("allega")}</th> </tr>
             <%}}%>
	</thead>
       
        <tbody>
        <% 
        for(StazioneMetereologica s: stazione){ %>
        
            <tr> 
                <td><%=s.getNome()%></td>  <td><%= s.getUbicazione().getLocAmm().getComune() %> </td><td><%= s.getUbicazione().getQuota() %> </td>
                <%if(part!=null && ((part.getRuolo().equals(Role.AMMINISTRATORE))||(part.getRuolo().equals(Role.AVANZATO))||(part.getRuolo().equals(Role.BASE)))){%>
                                <td class="text-center"> <a href="Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>" role="button"><span class="fa fa-search" ></span></a></td>
                                <%}else if(s.getPubblico()){%>
                                   <td class="text-center"> <a href="Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>" role="button"><span class="fa fa-search" ></span></a></td>
                 <%}else{%>
                                   <td></td>
                                   <%}%>
                <% if(part!=null &&( part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO)||(part.getRuolo().equals(Role.BASE) && s.getIdUtente()==part.getIdUtente()) )){%>
                        <td> <a href="Servlet?operazione=modificaStazione&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>" role="button"><span class="fa fa-edit"></span></td>
                        <td> <a  id="buttonElimina" onclick="elimina(<%=s.getIdStazioneMetereologica()%>);" role="button">
                                <span class="fa fa-times"></span>
                        </a> </td>

                        <td> <a href="Servlet?operazione=allegatiStazione&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>" role="button"><span class="fa fa-paperclip"></span></a></td>

                 <%}%> 
                
           </tr>
            <%}%>              
	</tbody>
     </table>
         </div>
      </div>
    
 
</div>
    <jsp:include page="footer.jsp"></jsp:include>
  </div>

</body>
</html>