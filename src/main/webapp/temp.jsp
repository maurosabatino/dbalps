<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.OperazioneUtente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
 

 <jsp:useBean id="partecipante" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="session" />
        <jsp:setProperty  name="partecipante" property="*"/>
         <jsp:useBean id="utente" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="request" />
        <jsp:setProperty  name="partecipante" property="*"/>

<jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
 <jsp:setProperty  name="locale" property="*"/>
<html>

<head>
    <!--CSS-->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>

   
    <!--JAVASCRIPT-->
    <script src="js/jquery-2.1.1.min.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/globalize.js"></script>
    <script src="js/globalize.culture.de-DE.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/SeparateDate.js"></script>
    <script src="js/personalLibrary.js"></script>
    <script src="js/bootstrapValidator.min.js"></script>
    <script src="js/jquery.sticky-kit.min.js"></script>
    <script src="js/jquery.stickyfooter.min.js"></script>
    
    <!--Google Maps-->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
    <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
    
<title>${locale.getWord("utente")}</title>
</head>
<body>
  <div class ="container">
<jsp:include page="header.jsp"></jsp:include>

       <div class="row">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-8">
     
         
 <div class="container-fluid">
  <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=utente.getNome()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("nome")}</h1> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=utente.getCognome()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("cognome")}</h1> </div>
    </div>
  </div>
    <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=utente.getUsername()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>Username</h1> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=utente.getEmail()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>Email</h1> </div>
    </div>
  </div>
    <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=utente.getRuolo()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("ruolo")}</h1> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=partecipante.getAttivo()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("attivo")}</h1> </div>
    </div>
  </div>
    <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=utente.getDataCreazione()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("datacreazione")}</h1> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-2"><h1> <%=utente.getDataUltimoAccesso()%> </h1></div>
      <div class="col-md-2 col-md-pull-9"><h1>${locale.getWord("ultimoAccesso")}</h1> </div>
    </div>
  </div>
  </div>




<div class="container-fluid">
 <table class="table" >
         <thead>
             <tr> <th>${locale.getWord("operazione")}</th> <th>${locale.getWord("tabella")}</th>  <th>${locale.getWord("data")}</th> <th> ${locale.getWord("tipo")}</th> <th> ${locale.getWord("nomeTipo")}</th> </tr>
	</thead>
       
        <tbody> 
             <%       
             for(OperazioneUtente op: utente.getOperazioni() ){ %>
			<tr>
                        <td><%= op.getOperazione() %> </td> <td> <%= op.getTabella() %></td>
			
                        <% String tipo="";
                            Timestamp data=new Timestamp(0);
                            String nome="";
                        if(op.getIdProcesso()!=0){
                            tipo=""+locale.getWord("processo");
                            data=op.getData();
                            nome=op.getNomeProcesso();
                        }
                        else if(op.getIdStazione()!=0){
                            tipo=""+locale.getWord("stazione");
                             data=op.getData();
                             nome=op.getNomeStazione();
                        }
                        else if(op.getDataInizio()!=null){
                            tipo="Login";
                             data=op.getDataInizio();
                        }
                        else{
                            tipo="Logout";
                             data=op.getDataFine();
                        }
                        %>
                        <td><%= data %></td> 
			<td><%= tipo %></td>
                        <td><%= nome  %></td>			
			</tr>
                        <%}%>
		</tbody>
     </table>
        <div id="pager" class="pager">
	
		<img src="img/first.png" class="first"/>
		<img src="img/prev.png" class="prev"/>
		<input type="text" class="pagedisplay"/>
		<img src="img/next.png" class="next"/>
		<img src="img/last.png" class="last"/>
		<select class="pagesize">
			<option selected="selected"  value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option  value="40">40</option>
		</select>
	
        </div>
  
  
  
  
  

</div> <!--fine parte centrale-->
 
     
 

</div> <!--fine parte centrale-->

      </div>
    
 

    <jsp:include page="footer.jsp"></jsp:include>
  </div>

</body>
</html>