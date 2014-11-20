
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.stazione.Sensori"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica.*"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.controller.ControllerDatabase"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Role"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:useBean id="locale" class="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua" scope="session" />
        <jsp:setProperty  name="locale" property="*"/>
        <jsp:useBean id="partecipante" class="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente" scope="session" />
        <jsp:setProperty  name="partecipante" property="*"/>
        <!--CSS-->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/selectize.bootstrap3.css"/>
        <link rel="stylesheet" type="text/css" href="css/layout.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrapValidator.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>

        <!--JAVASCRIPT-->
        <script src="js/jquery-2.1.1.min.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/globalize.js"></script>
        <script src="js/globalize.culture.de-DE.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrapValidator.min.js"></script>
        <script src="js/jquery.sticky-kit.min.js"></script>
        <script src="js/jquery.stickyfooter.min.js"></script>
        <script src="js/selectize.js"></script>
        <script src="js/json.js"></script>
        <script src="js/mappe.js"></script>
        <script src="js/validator.js"></script>

        <!--Google Maps-->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
        <title>dbalps</title>
    </head>
    <body>

        <div class="container">
            <jsp:include page="header.jsp"></jsp:include>

                <div class="row"> 
                    <div class="col-md-8">
                    <%if (partecipante != null && (partecipante.getRuolo().equals(Role.AMMINISTRATORE) || partecipante.getRuolo().equals(Role.AVANZATO))) {%>
                    <form action="Servlet" id="insertStazione"  method="POST" role="form">
                        <div class="panel panel-default"> <div class="panel-body"> <h4> ${locale.getWord("titoloStazione")} </h4>

                                <div class="form-group" >
                                    <div class="row">
                                        <div class="col-xs-6 col-md-6"><label for="nome">${locale.getWord("nome")}</label> <input type="text" name="nome" id="nome" class="form-control" placeholder="<%=locale.getWord("nome") %>" ></div>
		</div>
		<br>
		<div class="row">
			<div class="col-xs-6 col-md-4"><label for="aggregazionegiornaliera">${locale.getWord("aggregazioneTemporale")}<input type="text" name="aggregazioneGiornaliera"  id="aggregazionegiornaliera" class="form-control" placeholder="aggregazione giornaliera"></div>	
			<div class="col-xs-6 col-md-3"><label for="tipoaggregazione">${locale.getWord("tipoDiAggregazioneGiornaliera")}<input type="text" name="tipoaggregazione"  id="tipoaggregazione" class="form-control" placeholder="tipo aggregazione"></div>
		</div>
		<br>
		<div class="row">
		<div class="col-xs-6 col-md-4"><label for="datainizio">${locale.getWord("dataInizio")} <input type="text"  id="datainizio" name="datainizio" class="form-control" placeholder="datainizio"></div>
		<div class="col-xs-6 col-md-4"><label for="datafine">${locale.getWord("dataFine")}<input type="text" id="datafine" name="datafine" class="form-control" placeholder="datafine"></div>
		</div>
		<br>
		<div class="row">
                     <div class="col-xs-6 col-md-6">
                                            <label for="ente">${locale.getWord("ente")}</label>
                                            <select id="ente" name=ente class="form-control">
                                            <input type="hidden" id="idEnte" name="idEnte"/>
                                        </div>
		</div>
               
                 </div>

                                <div class="panel panel-default">
                                    <div class="panel-body"> 
                                        <h4>${locale.getWord("datiUbicazione")}</h4>
                                        <div class="row">
                                            <div class="form-group" >
                                                <div class="col-xs-6 col-md-6">
                                                    <label for="sottobacino">${locale.getWord("sottobacino")}</label>
                                                    <select type="text" id="sottobacino" name="sottobacino" class="form-control" placeholder=" ${locale.getWord("sottobacino")}"></select>
                                                </div>
                                                <div class="col-xs-6 col-md-6">
                                                    <label for="bacino">${locale.getWord("bacino")}</label>
                                                    <input readonly="readonly" type="text" id="bacino" name="bacino" class="form-control" placeholder=" ${locale.getWord("bacino")} ">
                                                    <input type="hidden" id="idSottobacino" name="idSottobacino"/>
                                                </div> 
                                            </div>
                                        </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-xs-6 col-md-3">
                                                    <label for="comune">${locale.getWord("comune")}</label>
                                                    <select id="comune" name="comune" class="form-control" placeholder=" ${locale.getWord("comune")}"></select>
                                                    <input type="hidden" id="idcomune" name="idcomune" />
                                                </div>

                                                <div class="col-xs-6 col-md-3">
                                                    <label for="provincia"> ${locale.getWord("provincia")} </label>
                                                    <input readonly="readonly" type="text" id="provincia" name="provincia" class="form-control" placeholder=" ${locale.getWord("provincia")} "/>
                                                </div>
                                                <div class="col-xs-6 col-md-3">
                                                    <label for="regione"> ${locale.getWord("regione")} </label>
                                                    <input readonly="readonly" type="text" id="regione" name="regione" class="form-control" placeholder=" ${locale.getWord("regione")} " />
                                                </div>
                                                <div class="col-xs-6 col-md-3"><label for="nazione"> ${locale.getWord("nazione")} </label>
                                                    <input readonly="readonly" type="text" id="nazione" name="nazione" class="form-control" placeholder=" ${locale.getWord("nazione")} " />
                                                </div>
                                            </div>

                                            <div id="controls">
                                                <br>
                                                <div class="row">
                                                    <div class="col-xs-6 col-md-4">
                                                        <label for="latitudine">${locale.getWord("latitudine")}</label>
                                                        <input type="text" id="latitudine" name="latitudine" class="form-control" placeholder=" ${locale.getWord("latitudine")} "/>
                                                    </div>
                                                    <div class="col-xs-6 col-md-4">
                                                        <label for="longitudine"> ${locale.getWord("longitudine")} </label>
                                                        <input type="text" id="longitudine" name="longitudine" class="form-control" placeholder=" ${locale.getWord("longitudine")} "/>
                                                    </div>
                                                    <div class="col-xs-6 col-md-4">
                                                        <button type="button" class="round-button" name="showMap" id="showMap">
                                                            <img class="img-circle" src="img/map-marker-th.png"/>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                       
                                        
                                            <div id="map_container" title="Location Map">
                                            <div id="map_canvas" style="width:100%;height:80%;"></div>
                                            <div class="row">
                                                <div class="col-xs-6 col-md-6">
                                                    <label for="lati"> ${locale.getWord("latitudine")} </label>
                                                    <input type="text" id ="lati" name="lati" class="form-control" placeholder=" ${locale.getWord("latitudine")} ">
                                                </div>
                                                <div class="col-xs-6 col-md-6">
                                                    <label for="long"> ${locale.getWord("longitudine")} </label>
                                                    <input type="text" id="long" name="long" class="form-control"  placeholder=" ${locale.getWord("longitudine")} ">
                                                </div>
                                            </div>
                                        </div>
                                                                                         
                                        <br>
                                        <div class="row">
                                            <div class="col-xs-6 col-md-6">
                                                <label for="quota"> ${locale.getWord("quota")} </label>
                                                <input type="text" id="quota" name="quota" class="form-control" placeholder=" ${locale.getWord("quota")} "/>
                                            </div>
                                            <div class="col-xs-6 col-md-6">
                                                <label for="esposizione"> ${locale.getWord("esposizione")} </label> 
                                                <select class="form-control" name="esposizione" id="esposizione">
                                                    <option value=""></option>
                                                    <option value=" ${locale.getWord("n")} "> ${locale.getWord("n")} </option>
                                                    <option value=" ${locale.getWord("ne")} "> ${locale.getWord("ne")} </option>
                                                    <option value=" ${locale.getWord("e")} "> ${locale.getWord("e")} </option>
                                                    <option value=" ${locale.getWord("se")} "> ${locale.getWord("se")} </option>
                                                    <option value=" ${locale.getWord("s")} "> ${locale.getWord("s")} </option>
                                                    <option value=" ${locale.getWord("so")} "> ${locale.getWord("so")} </option>
                                                    <option value=" ${locale.getWord("o")} "> ${locale.getWord("o")} </option>
                                                    <option value=" ${locale.getWord("no")} "> ${locale.getWord("no")} </option>
                                                </select>
                                            </div>
                                        </div>
                                        <br><div class="row">
                                                 <div class="col-md-6">
                                                <label for="${locale.getWord("caratteristicheSitoStazione")}">${locale.getWord("sito")}</label>
                                                <select id="${locale.getWord("caratteristicheSitoStazione")}" name="${locale.getWord("caratteristicheSitoStazione")}" class="form-control" placeholder="${locale.getWord("sito")}"></select>
                                                <input type="hidden" id="idsitostazione" name="idsitostazione"/>
                                            </div>
                                        </div>
                                    </div> </div>

                                <div class="panel panel-default"> <div class="panel-body">
                                        <h4> ${locale.getWord("sensori")} </h4>
                                        <p>
                                            <% for (Sensori s : ControllerDatabase.prendiTuttiSensori()) {
                                                    String sensore;
                                                    int id=s.getIdsensori();
                                                    if (locale.getLanguage().equals("it")) {
                                                        sensore = s.getSensori_IT();
                                                    } else {
                                                        sensore = s.getSensori_ENG();
                                                    }
                                            %>
                                            <input type="checkbox" name="sensori" value="<%=id%> "/>  <%=sensore%>  
                                            <% } %>
                                        </p>
                                        
                               </div>
                                <br><div class="wrapper">
                                    <div class="content-main">
                                        <label for="note"> ${locale.getWord("note")} </label>
                                    </div>
                                    <div class="content-secondary">
                                        <textarea rows="5" cols="100" name="note" id="note" class="textarea" placeholder="Note"></textarea>
                                    </div>
                                </div>

                                <input type="hidden" name="operazione" value="inserisciStazione">
                                <button type="submit" class="btn btn-default">${locale.getWord("inserisciStazione")}</button>
                                </div> </div></div>
                    </form>
                    <%} else { %>
                    <h3>Spiacente non hai i privilegi sufficienti per inserire una stazione</h3>
                    <% }%>    
                </div>
            </div>
            <div class="row">
                <jsp:include page="footer.jsp"></jsp:include>
            </div>

        </div>

    </body>
</html>
