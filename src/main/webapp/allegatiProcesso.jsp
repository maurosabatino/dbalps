

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Allegato"%>
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
                        data: {operazione: 'eliminaStazione', idstazione: arg},
                        success: function () {
                            window.location.reload();
                        }
                    });
                }
            }
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

                            <table id="tabella" class="table table-striped table-bordered table-condensed">
                                <thead>


                                    <tr> <th>Titolo </th><th>Tipo </th> <th>Nome file</th><th>Dettagli </th><th> Modifica</th>  </tr>

                                </thead>

                                <tbody>
                                <%  ArrayList<Allegato> allegati = (ArrayList<Allegato>) request.getAttribute("allegati");
                                    for (Allegato a : allegati) {
                                %>


                                <tr> 
                                    <%String nomeFile = a.getLinkFile();%>
                                    <td><%=a.getTitolo()%> </td>
                                    <td><%=a.getTipoAllegato()%></td>
                                    <td><a href="Servlet?operazione=downloadAllegato&file=<%=a.getLinkFile()%>"><%=nomeFile.substring(a.getLinkFile().lastIndexOf("\\") + 1)%></a></td>
                                    <td> <a href="Servlet?operazione=visualizzaAllegato&idAllegato=<%=a.getId()%>" ><span class="fa fa-search" ></span></a></td>
                                    <td> <a href="Servlet?operazione=modificaAllegato&idAllegato=<%=a.getId()%>" ><span class="fa fa-edit"></span></a></td>


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