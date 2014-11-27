<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.lang.Object"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.TipologiaProcesso"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.Processo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.*"%>
<!DOCTYPE html >



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
                    
                    $('#tabella').dataTable({
                        "language": {
                        "lengthMenu": "Display _MENU_ process per page",
                        "zeroRecords": "Nothing found - sorry",
                        "info": "Showing page _PAGE_ of _PAGES_ ",
                        "infoEmpty": "No process available",
                        "infoFiltered": "(filtered from _MAX_ total process)"
                    }
                    });
                });
                    
   
            </script> 


            <title>Visualizza processi</title>
        </head>
        <body>
            <div class ="container">
            <jsp:include page="header.jsp"></jsp:include>
                <div class="row container-fluid ">
                <jsp:include page="barraLaterale.jsp"></jsp:include>
            <%Utente part = (Utente) session.getAttribute("partecipante");%>

                    <div class="col-md-8"> 
                    
                        
                        <table id="tabella" class="table table-striped table-bordered">
                            <thead>
                                <tr> 
                                    <th>Nome</th>
                                    <th>Data</th>
                                    <th>Ora</th> 
                                    <th>Comune</th>
                                    <th>Tipologia</th>
                                    <th> Dettagli</th>
                                    <%if(part!=null && ((part.getRuolo().equals(Role.AMMINISTRATORE))||(part.getRuolo().equals(Role.AVANZATO)))){%>
                                    <th> Modifica</th>
                                    <th> Elimina</th>
                                    <%}%>
                                </tr>
                            </thead>
                            
                            <tbody>
                            <%
                                ArrayList<Processo> processo = (ArrayList<Processo>) request.getAttribute("processo");
                                for (Processo p : processo) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeInMillis(p.getData().getTime());%>

                            <tr> 
                                <td><%=p.getNome()%> </td>
                                <td><%="" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH)%></td>
                                <td><%="" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)%></td> 
                                <td><%= p.getUbicazione().getLocAmm().getComune()%> </td>
                                <% int i = 0;
                                    StringBuilder tipologia = new StringBuilder();
                                    for (TipologiaProcesso t : p.getAttributiProcesso().getTipologiaProcesso()) {
                                        if (!tipologia.toString().equals("")) {
                                            tipologia.append(" ,");
                                        }
                                        tipologia.append(t.getNome_IT());
                                    }
                                %>
                                <td><%=tipologia.toString()%></td>
                                <td> <a href="Servlet?operazione=mostraProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button"><img alt="Brand" class="img-responsive" src="img/search-icon (32).png"></a></td>
                                        <% 
                                            if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td> <a href="Servlet?operazione=mostraModificaProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button" ><img alt="Brand" class="img-responsive" src="img/edit-validated-icon.png"></a></td>
                                        <%}%>
                                        <%
                                            if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td> 
                                    <a id="buttonElimina" onclick="elimina(<%=p.getIdProcesso()%>)" role="button">
                                        <img alt="Brand" class="img-responsive" src="img/delete-icon.png">
                                    </a></td>
                                    <%}%>
                            </tr>
                            <%}%>   

                        </tbody>
                    </table>
                    </div>
            </div>
            <jsp:include page="footer.jsp"></jsp:include>
        </div>

    </body>
</html>