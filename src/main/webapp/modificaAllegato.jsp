<%-- 
    Document   : modificaProcesso
    Created on : 18-nov-2014, 15.45.23
    Author     : Mauro
--%>


<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.Processo"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.controller.ControllerDatabase"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Role"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Allegato"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
        <jsp:setProperty  name="locale" property="*"/>
        <jsp:useBean id="partecipante" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="session" />
        <jsp:setProperty  name="partecipante" property="*"/>
       
        
        <jsp:include page="import.jsp"></jsp:include>
            <script>
                 <% Allegato a=(Allegato)request.getAttribute("allegato");%>
                $(document).ready(function(){
                    $("#tipo").val(<%=a.getTipoAllegato()%>);
                      });
                    </script>
    </head>
    <body>

        <div class="container">
            <jsp:include page="header.jsp"></jsp:include>

                <div class="row"> 
                <jsp:include page="barraLaterale.jsp"></jsp:include>
                  <div class="col-md-8 ">
                     <form class="form-horizontal" action="Servlet" name="dati" method="POST" enctype="multipart/form-data" >
        <div class="panel panel-default"> <div class="panel-body"> <h4>allegato</h4></div>
        <br>
        <div class="form-group">
        <label for="autore" class="col-sm-2 control-label">${locale.getWord("autore")}</label>
        <div class="col-sm-10">
        <input type="text" name="autore" id="autore" class="form-control" value="<%=a.getAutore()%>">
        </div>
        </div>

        <div class="form-group">
        <label for="anno" class="col-sm-2 control-label">${locale.getWord("anno")}</label>
        <div class="col-sm-10">
        <input type="text" name="anno" id="anno" class="form-control" value="<%=a.getAnno()%>">
        </div>
        </div>

        <div class="form-group">
        <label for="titolo" class="col-sm-2 control-label">${locale.getWord("titolo")}</label>
        <div class="col-sm-10">
        <input type="text" name="titolo" id="titolo" class="form-control" value="<%=a.getTitolo()%>">
        </div>
        </div>

        <div class="form-group">
        <label for="in" class="col-sm-2 control-label">${locale.getWord("in")}</label>
        <div class="col-sm-10">
        <input type="text" name="in" id="in" class="form-control" value="<%=a.getNella()%>">
        </div>
        </div>

        <div class="form-group">
        <label for="fonte" class="col-sm-2 control-label">${locale.getWord("fonte")}</label>
        <div class="col-sm-10">
        <input type="text" name="fonte" id="fonte" class="form-control"  value="<%=a.getFonte()%>">
        </div>
        </div>

        <div class="form-group">
        <label for="urlWeb" class="col-sm-2 control-label">${locale.getWord("urlDelSito")}</label>
        <div class="col-sm-10">
        <input type="text" name="urlWeb" id="urlWeb" class="form-control" value="<%=a.getUrlWeb()%>" >
        </div>
        </div>

        <div class="form-group">
        <label for="note" class="col-sm-2 control-label">${locale.getWord("note")}</label>
        <div class="col-sm-10">
        <input type="text" name="note" id="note" class="form-control" value="<%=a.getNote()%>" >
        </div>
        </div>

        <div class="form-group">
        <label for="tipo" class="col-sm-2 control-label">${locale.getWord("tipo")}</label>
        <div class="col-sm-10">
        <select class="form-control" name="tipo" id="tipo">
        <option value="document"  >Document</option>
       
     
        <option value="map">Map </option> 
      
       <option value="image">Image</option> 
        
         <option value="Link">Link</option> 
        </select>
        </div>
        </div>

       
        <input type="hidden" name="idAllegato" value="<%=a.getId() %>">
        <input type="hidden" name="operazione" value="modificaAllegatoSuDB">
        <div class="form-group">
        <div class="col-sm-10">
         <button type="submit" class="btn btn-default">${locale.getWord("modifica")}</button>
        </div>
        </div>

        </div>
        </form>
                </div>     
                </div>
            </div>
            <div class="row">
                <jsp:include page="footer.jsp"></jsp:include>
            </div>

    

            
                  
    </body>
</html>

