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
        <jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
        <jsp:setProperty  name="locale" property="*"/>
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
 <script>
        function elimina(arg) {
            var domanda = confirm("Sei sicuro di voler cancellare?");
            if (domanda === true) {
     
        $.ajax({            
            url: 'Servlet',
            type: 'POST',   
            data: {operazione: 'eliminaProcesso', idProcesso:arg},
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
                <div class="row container-fluid ">
                <jsp:include page="barraLaterale.jsp"></jsp:include>
            <%Utente part = (Utente) session.getAttribute("partecipante");%>

                    <div class="col-md-8"> 
                        <%ArrayList<Processo> processo = (ArrayList<Processo>) request.getAttribute("processo");%>
                    <h2>${locale.getWord("allProcess")} (<%=processo.size()%>)</h2>
                        
                        <table id="tabella" class="table table-striped table-bordered table-condensed">
                            <thead>
                                <tr> 
                                    <th>${locale.getWord("nome")}</th>
                                    <th>${locale.getWord("data")}</th>
                                    <th>${locale.getWord("ora")}</th> 
                                    <th>${locale.getWord("comune")}</th>
                                    <th>${locale.getWord("tipologia")}</th>
                                    <th> ${locale.getWord("dettagli")}</th>
                                    <%if(part!=null && ((part.getRuolo().equals(Role.AMMINISTRATORE))||(part.getRuolo().equals(Role.AVANZATO)))){%>
                                    <th> ${locale.getWord("modifica")}</th>
                                    <th> ${locale.getWord("elimina")}</th>
                                    <th> ${locale.getWord("allegati")}</th>
                                    <%}%>
                                </tr>
                            </thead>
                            
                            <tbody>
                            <%
                                
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
                                
                                <%if(part!=null && ((part.getRuolo().equals(Role.AMMINISTRATORE))||(part.getRuolo().equals(Role.AVANZATO))||(part.getRuolo().equals(Role.BASE)))){%>
                                <td class="text-center"> <a href="Servlet?operazione=mostraProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button"><span class="fa fa-search" ></span></a></td>
                                <%}else if(p.getAttributiProcesso().isPubblico()){%>
                                   <td class="text-center"> <a href="Servlet?operazione=mostraProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button"><span class="fa fa-search" ></span></a></td>
                                 <%}else{%>
                                   <td></td>
                                   <%}%>
                                <% if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td class="text-center"> <a href="Servlet?operazione=mostraModificaProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button" ><span class="fa fa-edit"></span></a></td>
                                        <%}%>
                                        <%if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td class="text-center"> 
                                    <a id="buttonElimina" onclick="elimina(<%=p.getIdProcesso()%>)" role="button">
                                        <span class="fa fa-times"></span>
                                    </a></td>
                                     <%}%>
                                    <%if(part!=null && ((part.getRuolo().equals(Role.AMMINISTRATORE))||(part.getRuolo().equals(Role.AVANZATO))||(part.getRuolo().equals(Role.BASE)))){%>
                                  <td class="text-center">
                                        <a href="Servlet?operazione=mostraAllegatiProcesso&idProcesso=<%=p.getIdProcesso()%>" >
                                            <span class="fa fa-paperclip"></span>
                                        </a>
                                    </td>
                                   
                                    <%}%>
                                  <%}%>
                            </tr>
                              

                        </tbody>
                    </table>
                    </div>
            </div>
            <jsp:include page="footer.jsp"></jsp:include>
        </div>

    </body>
</html>