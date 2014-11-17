
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.*"%>
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
        <script src="js/personalLibrary.js"></script>
        <script src="js/bootstrapValidator.min.js"></script>
        <script src="js/jquery.sticky-kit.min.js"></script>
        <script src="js/jquery.stickyfooter.min.js"></script>
        <script src="js/selectize.js"></script>
        <script src="js/json.js"></script>

        <!--Google Maps-->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
        <title>dbalps</title>
    </head>
    <body>

        <div class="container">
            <jsp:include page="header.jsp"></jsp:include>

                <div class="row"> 
                <jsp:include page="barraLaterale.jsp"></jsp:include>
                    <div class="col-md-8">
                    <%if (partecipante != null && (partecipante.getRuolo().equals(Role.AMMINISTRATORE) || partecipante.getRuolo().equals(Role.AVANZATO))) {%>
                    <form action="Servlet" class="insertProcesso"  method="POST" role="form">
                        <div class="panel panel-default"> <div class="panel-body"> <h4> ${locale.getWord("titoloProcesso")} </h4>

                                <div class="form-group" >
                                    <div class="row">

                                        <div class="col-xs-6 col-md-6"><label for="nome"> ${locale.getWord("nome")}</label> <input type="text" name="nome" id="nome" class="form-control" placeholder="${locale.getWord("nome")}"/></div>
                                    </div>
                                    <br><div class="row ">
                                        <div class="col-xs-6 col-md-3"><label for="anno"> ${locale.getWord("anno")}</label> 
                                            <input type="text" id="anno" name="anno"  class="form-control" placeholder="${locale.getWord("anno")} "></div>

                                        <div class="col-xs-6 col-md-3"><label for="mese"> ${locale.getWord("mese")} </label>
                                            <select id="mese" name="mese" class="form-control"/>
                                            <option value="vuoto"> </option>
                                            <%for (int i = 1; i <= 12; i++) {%>
                                            <option value="<%=i%>"><%=i%></option>
                                            <%}%>
                                            </select>
                                        </div>
                                        <div class="col-xs-6 col-md-3"><label for="giorno"> ${locale.getWord("giorno")} </label> 
                                            <select id="giorno" name="giorno" class="form-control"/>
                                            <option value="vuoto"> </option>
                                            <%for (int i = 1; i <= 31; i++) {%>
                                            <option value="<%=i%>"> <%=i%></option>
                                            <%}%>
                                            </select>
                                        </div>
                                        <div class="col-xs-6 col-md-3"><label for="ora"> ${locale.getWord("ora")} </label> <input type="text" id="ora" name="ora"  class="form-control" placeholder=" ${locale.getWord("ora")} "></div> 
                                    </div>

                                    <br>
                                    <div class="row">
                                        <div class="col-xs-6 col-md-4">
                                            <label for="superficie">${locale.getWord("superficie")}</label>
                                            <input type="text" name="superficie" id="superficie" class="form-control" placeholder="${locale.getWord("superficie")}"/>
                                        </div>
                                        <div class="col-xs-6 col-md-4">
                                            <label for="larghezza"> ${locale.getWord("larghezza")}</label>
                                            <input type="text" name="larghezza" id="larghezza" class="form-control" placeholder="${locale.getWord("larghezza")}">
                                        </div>
                                        <div class="col-xs-6 col-md-4">
                                            <label for="altezza">${locale.getWord("altezza")}</label>
                                            <input type="text" name="altezza" id="altezza" class="form-control" placeholder="${locale.getWord("altezza")}">
                                        </div>
                                    </div>
                                    <br/>
                                    <div class="row">
                                        <div class="col-xs-6 col-md-6">
                                            <label for="volumeSpecifico">${locale.getWord("volumeSpecifico")}</label>
                                            <input type="text" name=volumespecifico id="volumeSpecifico" onkeypress="return numberOnly(event)" class="form-control" placeholder=" ${locale.getWord("volumeSpecifico")} " ></div>
                                        <div class="col-xs-6 col-md-6">
                                            <label for="intervallo">${locale.getWord("intervallo")}</label>
                                            <select id="intervallo" name=intervallo class="form-control">
                                            <input type="hidden" id="idclasseVolume" name="idclasseVolume"/>
                                        </div>
                                        
                                    </div>

                                    <br>
                                    <div class="wrapper">
                                        <div class="content-main">
                                            <label for="descrizione"> ${locale.getWord("descrizione")} </label>
                                        </div>
                                        <div class="content-secondary">
                                            <textarea rows="5" cols="100" name="descrizione" id="descrizione" class="textarea" placeholder=" ${locale.getWord("descrizione")} "></textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <h4> ${locale.getWord("tipologiaProcesso")} </h4>
                                    <div class="panel panel-default"> 
                                        <div class="panel-body">
                                            <p>
                                                <%for (TipologiaProcesso tp : ControllerDatabase.prendiTipologiaProcesso()) {
                                                        String tipoProc;
                                                        if (locale.getLanguage().equals("it")) {
                                                            tipoProc = tp.getNome_IT();
                                                        } else {
                                                            tipoProc = tp.getNome_ENG();
                                                        }%>
                                                <input type="checkbox" name=" ${locale.getWord("tipoProcesso")} " value=" <%=tipoProc%> "/>  <%=tipoProc%> 
                                                <%}%>
                                            </p>

                                        </div> </div>
                                </div>

                                <div class="panel panel-default"> <div class="panel-body"> <h4>Dati sull'ubicazione</h4>
                                        <div class="row">
                                            <div class="form-group" >
                                                <div class="col-xs-6 col-md-6">
                                                    <label for="sottobacino">${locale.getWord("sottobacino")}</label>
                                                    <input type="text" id="sottobacino" name="sottobacino" class="form-control" placeholder=" ${locale.getWord("sottobacino")} "/>
                                                </div>
                                                <div class="col-xs-6 col-md-6">
                                                    <label for="bacino">${locale.getWord("bacino")}</label>
                                                    <input readonly="readonly" type="text" id="bacino" name="bacino" class="form-control" placeholder=" ${locale.getWord("bacino")} "/>
                                                    <input type="hidden" id="idSottobacino" name="idSottobacino"/>
                                                </div> 
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-xs-6 col-md-3">
                                                    <label for="comune">${locale.getWord("comune")}</label>
                                                    <input type="text" id="comune" name="comune" class="form-control" placeholder=" ${locale.getWord("comune")} "/>
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
                                                <br><div class="row">
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
                                        <br><div class="row">
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
                                            <div class="col-xs-6 col-md-6">
                                                <label for="attendibilita"> ${locale.getWord("attendibilita")} </label> 
                                                <select class="form-control" name="attendibilita" id="attendibilita">
                                                    <option value=""></option>
                                                    <option value=" ${locale.getWord("puntuale")} "> ${locale.getWord("puntuale")} </option>
                                                    <option value=" ${locale.getWord("areale")} "> ${locale.getWord("areale")} </option>
                                                    <option value=" ${locale.getWord("indicativa")} "> ${locale.getWord("indicativa")} </option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="${locale.getWord("caratteristicaSito")}">${locale.getWord("sito")}</label>
                                                <select id="${locale.getWord("caratteristicaSito")}" name="${locale.getWord("caratteristicaSito")}" class="form-control" placeholder="${locale.getWord("sito")}"/>
                                                <input type="hidden" id="idsito" name="idsito"/>
                                            </div>
                                        </div>
                                    </div> </div>

                                <div class="panel panel-default"> <div class="panel-body">
                                        <h4> ${locale.getWord("danni")} </h4>
                                        <p>
                                            <% for (Danni d : ControllerDatabase.prendiDanni()) {
                                                    String tipoDanno;
                                                    if (locale.getLanguage().equals("it")) {
                                                        tipoDanno = d.getTipo_IT();
                                                    } else {
                                                        tipoDanno = d.getTipo_ENG();
                                                    }
                                            %>
                                            <input type="checkbox" name=" ${locale.getWord("tipoDanno")} " value="<%=tipoDanno%> "/>  <%=tipoDanno%>  
                                            <% } %>
                                        </p>
                                        
                                        <h4>Effetti Morfologici</h4>
                                        <p>
                                        <%for (EffettiMorfologici em : ControllerDatabase.prendiEffettiMOrfologici()) {
                                                String effMorfologici;
                                                if (locale.getLanguage().equals("it")) {
                                                    effMorfologici = em.getTipo_IT();
                                                } else {
                                                    effMorfologici = em.getTipo_ENG();
                                                }%>
                                        <input type="checkbox" name="${locale.getWord("effMorfologici")}" value="<%=effMorfologici%>"/>  <%=effMorfologici%>  
                                        <%}%>
                                        </p>
                                        <br><div class="row">
                                            <div class="col-xs-6 col-md-6">
                                                <label for="gradoDanno"> ${locale.getWord("gradoDanno")} </label> 
                                                <select class="form-control" name="gradoDanno" id="gradoDanno">
                                                    <option value=""></option>
                                                    <option value=" ${locale.getWord("danneggiato")} "> ${locale.getWord("danneggiato")} </option>
                                                    <option value=" ${locale.getWord("distrutto")} "> ${locale.getWord("distrutto")} </option>
                                                    <option value=" ${locale.getWord("minacciato")} "> ${locale.getWord("minacciato")} </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div> </div>

                                <div class="panel panel-default"> <div class="panel-body"> <h4> ${locale.getWord("litologiaTitolo")} </h4>


                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="${locale.getWord("lito")}"> ${locale.getWord("litologia")} </label> 
                                                <select id="${locale.getWord("lito")}" name="${locale.getWord("lito")}" class="form-control" placeholder="${locale.getWord("litologia")}"/>
                                                <input type="hidden" id="idLitologia" name="idLitologia" />
                                            </div>
                                            <div class="col-xs-6 col-md-6">
                                                <label for="${locale.getWord("proprietaTermiche")}"> ${locale.getWord("propTermiche")} </label>
                                                <select id="${locale.getWord("proprietaTermiche")}" name="${locale.getWord("proprietaTermiche")}" class="form-control" placeholder="${locale.getWord("propTermiche")}"/>
                                                <input type="hidden" id="idProprietaTermiche" name="idProprietaTermiche" />
                                            </div>
                                        </div>
                                            <div class="row">
                                            <div class="col-xs-6 col-md-6">
                                                <label for="${locale.getWord("statoFrattura")}">${locale.getWord("statoFratturazione")} </label>
                                                <select type="text" id="${locale.getWord("statoFrattura")}" name="${locale.getWord("statoFrattura")}" class="form-control" placeholder="${locale.getWord("statoFratturazione")}"/>
                                                <input type="hidden" id="idStatoFratturazione" name="idStatoFratturazione" />
                                            </div>
                                            
                                        </div>
                                    </div> </div>

                                <br><div class="wrapper">
                                    <div class="content-main">
                                        <label for="note"> ${locale.getWord("note")} </label>
                                    </div>
                                    <div class="content-secondary">
                                        <textarea rows="5" cols="100" name="note" id="note" class="textarea" placeholder="Note"></textarea>
                                    </div>
                                </div>

                                <input type="hidden" name="operazione" value="inserisciProcesso">
                                <button type="submit" class="btn btn-default">Inserisci il processo</button>
                            </div> </div>
                    </form>
                    <%} else { %>
                    <h3>Spiacente non hai i privilegi sufficienti per inserire un processo</h3>
                    <% }%>    
                </div>
            </div>
            <div class="row">
                <jsp:include page="footer.jsp"></jsp:include>
            </div>

        </div>

    </body>
</html>
