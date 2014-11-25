<%@page import="java.util.Calendar"%>
<%@page import="java.lang.Object"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.TipologiaProcesso"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.Processo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >

<jsp:useBean id="partecipante" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="session" />
<jsp:setProperty  name="partecipante" property="*"/>

<html>

    <head>
        <jsp:include page="import.jsp"></jsp:include>
            <script>
                $(document).ready(function () {

                    $("table").tablesorter({
                        debug: true,
                        sortList: [[0, 0]]
                    });


                    function elimina(arg) {
                        var domanda = confirm("Sei sicuro di voler cancellare?");
                        if (domanda === true) {
                            $.ajax({
                                url: 'Servlet',
                                type: 'POST',
                                data: {operazione: 'eliminaProcesso', idProcesso: arg},
                                success: function () {
                                    window.location.reload();
                                }
                            });
                        }
                    }
                });
            </script>


            <title>Visualizza processi</title>
        </head>
        <body>
            <div class ="container">
            <jsp:include page="header.jsp"></jsp:include>
                <div class="row">
                <jsp:include page="barraLaterale.jsp"></jsp:include>
                    <div class="col-md-8">

                        <table class="table">
                            <thead>
                                <tr> 
                                    <th>Nome</th>
                                    <th>Data</th>
                                    <th>Ora</th> 
                                    <th>Comune</th>
                                    <th>Tipologia</th>
                                    <th> Dettagli</th>
                                    <th> Modifica</th>
                                    <th> Elimina</th>
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
                                        <% Utente part = (Utente) session.getAttribute("partecipante");
                                            if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td> <a href="Servlet?operazione=mostraModificaProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button" ><img alt="Brand" class="img-responsive" src="img/edit-validated-icon.png"></a></td>
                                        <%}%>
                                        <%
                                            if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td> <a id="buttonElimina" onclick="elimina(<%=p.getIdProcesso()%>);" role="button">
                                        <img alt="Brand" class="img-responsive" src="img/delete-icon.png">
                                    </a></td>
                                    <%}%>
                            </tr>
                            <%}%>   

                        </tbody>
                    </table>

                </div>
            </div>

        </div>

    </body>
</html>