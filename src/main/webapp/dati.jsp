
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="java.util.Calendar"%>
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
            DateFormatSymbols format = new DateFormatSymbols();
            int anno = 0;
            for(datoClimatico d:dati){
            if(d.getData().get(Calendar.YEAR)!=anno){
                anno = d.getData().get(Calendar.YEAR);
            
        %>
         <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
            <%=anno%>
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
          <div role="tabpanel">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
      <% for(int month = 0;month<12;month++){%>
        <li role="presentation"><a href="#<%=""+anno+"_"+month%>" aria-controls="home" role="tab" data-toggle="tab"><%=format.getShortMonths()[month]%></a></li>
      <%}%>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
      <% for(int month = 0;month<12;month++){%>
      
    <div role="tabpanel" class="tab-pane active" id="<%=""+anno+"_"+month%>">
        <table>
            <thead>
                <tr>data</tr><tr>dato</tr>
            </thead>
            <tr><%=d.getData().get(Calendar.DATE)%></tr>
            <tr><%=d.getDato()%></tr>
        </table>
    </div>
    <%}%>
  </div>

</div>
      </div>
    </div>
  </div>
         </div>
     
     
     
     
     
     
     
     
     
     
     
     <%}}%>
   
    
    
    
    
    
    
    
    
    
    
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