<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.OperazioneUtente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
 

 <jsp:useBean id="utente" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="session" />
        <jsp:setProperty  name="utente" property="*"/>
         

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
    <script src="js/jquery-3.1.1.min.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/globalize.js"></script>
    <script src="js/globalize.culture.de-DE.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/SeparateDate.js"></script>
    <script src="js/personalLibrary.js"></script>
    <script src="js/bootstrapValidator.min.js"></script>
    <script src="js/jquery.sticky-kit.min.js"></script>
    <script src="js/jquery.stickyfooter.min.js"></script>
    <script type="text/javascript" src="js/jquery-latest.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>
    
    <!--Google Maps-->

    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
    <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
    
     <script type="text/javascript">
	$(function() {
		$("table")
			.tablesorter({debug: true
                            })
			.tablesorterPager({container: $("#pager")});
                        
                
	});
	</script>
    
    
   
<title>${locale.getWord("utente")}</title>
</head>
<body>
  <div class ="container">
<jsp:include page="header.jsp"></jsp:include>

       <div class="row">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-8">
     
         <% Utente u= (Utente)request.getAttribute("utente");%>
 <div class="container-fluid">
     
  <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getNome()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>${locale.getWord("nome")}</h3> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getCognome()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>${locale.getWord("cognome")}</h3> </div>
    </div>
  </div>
    <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getUsername()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>Username</h3> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getEmail()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>Email</h3> </div>
    </div>
  </div>
    <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getRuolo()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>${locale.getWord("ruolo")}</h3> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getAttivo()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>${locale.getWord("attivo")}</h3> </div>
      <button id="abilitato" class="img-circle">
         abilita
      </button>
    </div>
  </div>
    <div class="row">
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getDataCreazione()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>${locale.getWord("dataCreazione")}</h3> </div>
    </div>
    <div class="row">
        <div class="col-md-9 col-md-push-3"><h3> <%=u.getDataUltimoAccesso()%> </h3></div>
      <div class="col-md-3 col-md-pull-9"><h3>${locale.getWord("ultimoAccesso")}</h3> </div>
    </div>
  </div>
  </div>



    <br>
    <br>
<div class="container-fluid">
 <table class="table" >
         <thead>
             <tr> <th>${locale.getWord("operazione")}</th> <th>${locale.getWord("tabella")}</th>  <th>${locale.getWord("data")}</th> <th> ${locale.getWord("tipo")}</th> <th> ${locale.getWord("nomeTipo")}</th> </tr>
	</thead>
       
        <tbody> 
             <%       
             for(OperazioneUtente op: u.getOperazioni() ){ %>
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
<script>
        $(document).ready(function () {
         $("#abilitato").click(function () {
        $.ajax({            
            url: 'Servlet',
            type: 'POST',   
            data: {operazione: 'abilitaUtente', abilitato:'${utente.attivo}', id:'${utente.idUtente}'},
            success: function () {
               window.location.reload();
            }
        });
    });  });</script>
</body>
</html>