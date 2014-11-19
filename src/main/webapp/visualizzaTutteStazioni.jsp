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
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>
    <link rel="stylesheet" type="text/css" href="css/tabella.css"/>


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
    
    <!--table sorter-->
    <script type="text/javascript" src="js/jquery-latest.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>
    	<script src="js/jquery.tablesorter.widgets.js"></script>

    <script type="text/javascript">
	$(function() {
		$("table")
			.tablesorter({debug: true
                            })
			.tablesorterPager({container: $("#pager")});
                        
                
	});
	</script>
       
    <!--Google Maps-->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
    <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
    
<title>Visualizza stazioni</title>
</head>
<body>
  <div class ="container">
<jsp:include page="header.jsp"></jsp:include>
   <div class ="content">
       <div class="row">
      <jsp:include page="barraLaterale.jsp"></jsp:include>
     <div class="col-md-9 col-md-offset-3 col-lg-9 col-lg-offset-3 main">
         <table class="table" >
         <thead>
             <tr> <th>Nome </th>
                    <th>Comune </th> <th>Quota </th> <th> Dettagli</th> <th> Modifica</th>    </tr>
	</thead>
       
        <tbody>
        <%
            ArrayList<StazioneMetereologica> stazione = (ArrayList<StazioneMetereologica>) request.getAttribute("stazione");
        for(StazioneMetereologica s: stazione ){ %>
        
            <tr> 
                <td><span class="tabord"><%=s.getNome()%> </span></td>  <td><%= s.getUbicazione().getLocAmm().getComune() %> </td><td><%= s.getUbicazione().getQuota() %> </td>
                <td> <a href="Servlet?operazione=mostraStazioneMetereologica&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>">Dettagli</a></td>
                
                <% Utente part = (Utente) session.getAttribute("partecipante");
                        if(part!=null &&( part.getRuolo().equals(Role.AMMINISTRATORE)||part.getRuolo().equals(Role.AVANZATO)||(part.getRuolo().equals(Role.BASE) && s.getIdUtente()==part.getIdUtente()) )){%>
                <td> <a href="Servlet?operazione=modificaStazione&idStazioneMetereologica=<%=s.getIdStazioneMetereologica()%>">Modifica</a></td>
                 <%}%>  
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
      <jsp:getProperty name="HTMLc" property="content"/>
      </div>
    </div>
    
 
</div>
    <jsp:include page="footer.jsp"></jsp:include>
  </div>

</body>
</html>