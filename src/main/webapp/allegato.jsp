<%@page import="java.util.ArrayList"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Allegato"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >

<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<jsp:useBean id="allegato" class="it.cnr.to.geoclimalp.dbalps.bean.Allegato" scope="request" />
<jsp:setProperty  name="allegato" property="*"/>

<jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
<jsp:setProperty  name="locale" property="*"/>
<html>

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/layout.css"/>
        <script src ="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>


 
</head>
<body>
    <div class ="container">
        <jsp:include page="header.jsp"></jsp:include>

            <div class="container-fluid">
            <jsp:include page="barraLaterale.jsp"></jsp:include>
                <div class="col-md-8 ">



                   
                        
                            <br>

                            <div class="row">
                                <div class="col-md-9 col-md-push-3"><h2> <%=allegato.getTitolo() %> </h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("titolo")}</h2> </div>

                        </div>
                            <br>
                        <div class="row">
                            <% String data=""; 
                            if(allegato.getData()!=null) data=allegato.getData().toString(); %>
                            <div class="col-md-9 col-md-push-3"><h2><%= data %></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("data")} </h2> </div>
                        </div>
                 <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><%= allegato.getAutore() %></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("autore")} </h2> </div>
                        </div>
                        
                 <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><%= allegato.getAnno() %></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("anno")} </h2> </div>
                        </div>
                        
                        
                  <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><%= allegato.getFonte() %></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("fonte")} </h2> </div>
                        </div>
                    <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><%= allegato.getUrlWeb()%> </h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("urlDelSito")} </h2> </div>
                        </div>
                     <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><%= allegato.getNella()  %></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("in")} </h2> </div>
                        </div>
                     <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><%= allegato.getNote() %></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("note")} </h2> </div>
                        </div>
                     <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><%= allegato.getTipoAllegato() %></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("tipo")} </h2> </div>
                        </div> 
                   <div class="row">
                            <div class="col-md-9 col-md-push-3"><h2><a href="Servlet?operazione=downloadAllegato&file=<%=allegato.getLinkFile()%>"><%=allegato.getLinkFile().substring(allegato.getLinkFile().lastIndexOf("\\") + 1)%></a></h2></div>
                            <div class="col-md-2 col-md-pull-9"><h2>${locale.getWord("nomeFile")} </h2> </div>
                        </div> 
                </div>




                
               

  


        </div>


        <br/>
        <jsp:include page="footer.jsp"></jsp:include>
        </div>
  




</body>
</html>