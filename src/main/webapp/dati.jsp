
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.datoClimatico"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.*"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
 

 <jsp:useBean id="partecipante" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="session" />
        <jsp:setProperty  name="partecipante" property="*"/>
         

<jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
 <jsp:setProperty  name="locale" property="*"/>
<html>

<head>
    <jsp:include page="import.jsp"></jsp:include>
    <script type="text/javascript" src="js/jquery-latest.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>
    
   
   
<title>${locale.getWord("datiClimatici")}</title>
</head>
<body>
  <div class ="container">
<jsp:include page="header.jsp"></jsp:include>

       <div class="row">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-8">
     
          
 <div class="container-fluid">
     <%if(partecipante!=null && ((partecipante.getRuolo().equals(Role.AMMINISTRATORE))||(partecipante.getRuolo().equals(Role.AVANZATO)))){%>
   
        <% ArrayList<datoClimatico> dati=(ArrayList<datoClimatico> ) request.getAttribute("dati");
        for(datoClimatico d:dati){%>
         <div class="row">
        <div class="col-md-9 col-md-push-3"><h4> <%=d.getDato()%> </h4></div>
      <div class="col-md-3 col-md-pull-9"><h4><%=d.getData()%></h4> </div>
    </div>
    <%}%>
   <form action="Servlet" name="download" method="POST" >
                         <input type="submit" name ="submit"  value="download" >
			 <input type="hidden" name="operazione" value="scaricaDatiClimatici">
                         <input type="hidden" name="idStazione" value="<%=request.getAttribute("id")%>">
                         <input type="hidden" name="op" value="<%=request.getAttribute("op")%>">
			 </form>

    <%}else{%>
         <div class="row">
        <div class="col-md-3 col-md-pull-9"><h3>${locale.getWord("noDiritti")}</h3> </div>
      
    </div>
        <%}%>
  </div>

        

    <br>
    <br>

     
 

</div> <!--fine parte centrale-->

      </div>
    
 

    <jsp:include page="footer.jsp"></jsp:include>
  </div>

</body>
</html>