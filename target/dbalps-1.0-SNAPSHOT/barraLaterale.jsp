
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Role"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente"%>


       
            <div class="col-md-2 col-md-offset-1" id=login>

                <ul class="nav nav-sidebar">
                    <li>
                        <a><img class="" alt="HOME" src="img/home-icon .png"  id="home"></a>
                    </li>
                </ul>
                <ul class="nav nav-sidebar">
                    <li>
                        <a><img class="img-circle" id="buttonIT" alt="IT" src="img/italy-icon.png"> 
                        
                        <img class = "img-circle" id="buttonENG" alt="ENG" src="img/uk-icon.png"></a>
                    </li>
                </ul>  
                

                <ul class="nav nav-sidebar">
                    <% Utente part = (Utente) session.getAttribute("partecipante");
                        if (part == null) {
                    %>	
                    <li><button class="btn btn-info navbar-btn " data-toggle="modal" data-target=".login-form">Login</button></li>
                    <%} else {%>
                    <li>
                        <form   action="Servlet" name="dati" method="POST">
                            <input type=hidden name=operazione value=logout>
                            <a href="Servlet?operazione=mostraUtente&user=<%=part.getUsername()%>"><%=part.getUsername()%>  </a><button type="submit" class="btn btn-warning navbar-btn btn-sm">Logout</button>
                        </form>
                    </li>
                    <% }%>
                </ul>
                
                <ul class="nav nav-sidebar">
                    <li class="dropdown">
                        <%if (part != null && ((part.getRuolo().equals(Role.AMMINISTRATORE)) || (part.getRuolo().equals(Role.AVANZATO)))) {%>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${locale.getWord("inserisci")}<b class="caret"></b></a>
                            <%} else {%>
                        <a href="#" class="dropdown-toggle disabled" data-toggle="dropdown">${locale.getWord("inserisci")}<b class="caret"></b></a>
                            <%}%>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=formInserisciProcesso">${locale.getWord("inserisciProcesso")}</a></li>
                            <li><a href="Servlet?operazione=formInserisciStazione">${locale.getWord("inserisciStazione")}</a></li>
                            <li><a href="Servlet?operazione=elencocaricaDatiClimatici">${locale.getWord("inserisciDati")}</a></li>
                            <li><a href="Servlet?operazione=scegliProcessoAllegato">${locale.getWord("allegaProcesso")}</a></li>
                            <li><a href="Servlet?operazione=scegliStazioneAllegato">${locale.getWord("allegaStazione")}</a></li>

                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${locale.getWord("mostra")}<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=mostraTuttiProcessiModifica">${locale.getWord("tuttiProcessi")}</a></li>
                            <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche">${locale.getWord("tutteStazioni")}</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${locale.getWord("ricerca")} <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=queryProcesso">${locale.getWord("ricercaProcesso")}</a></li>
                            <li><a href="Servlet?operazione=queryStazione">${locale.getWord("ricercaStazione")}</a></li>
                            <li><a href="Servlet?operazione=queryClimatiche"> ${locale.getWord("climatiche")}</a></li>
                            <li><a href="Servlet?operazione=listaElaborazioni">${ locale.getWord("elaborazioni")}</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <%if (part != null && part.getRuolo().equals(Role.AMMINISTRATORE)) {%>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${locale.getWord("areaRiservata")}<b class="caret"></b></a>
                            <% } else { %>
                        <a href="#" class="dropdown-toggle disabled" data-toggle="dropdown">${locale.getWord("areaRiservata")}<b class="caret"></b></a>
                            <%}%>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=formCreaUtente">${locale.getWord("creaUtente")}</a></li>
                            <li><a href="Servlet?operazione=visualizzaTuttiUtenti">${locale.getWord("visualizzaTuttiUtenti")}</a></li>
                            <li><a href="Servlet?operazione=ricaricaJson">${locale.getWord("aggiornamenu")}</a></li>
                        </ul>
                    </li>


                </ul>
                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#"> ${locale.getWord("documentazione")} </a></li>
                    <li><a href="#">Info</a></li>
                </ul>

                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#">${locale.getWord("appAndroid")}</a></li>
                </ul>

                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#">${locale.getWord("statistiche")}</a></li>
                </ul>
            </div>
                        
        <div class="modal login-form" tabindex="-6" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form action="Servlet" id="login"  name="dati" method="POST">
                        <div class="form-group">
                            <br>
                            <br>
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1">
                                    <label for="username">Username </label><input type="text" name="username" id="username" class="form-control" placeholder="username"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1">
                                    <label for="password">Password </label><input type="password" name="password" id = "password" class="form-control" placeholder="password"/>
                                    <input type="hidden" name="operazione" value="login"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10 col-md-offset-4">
                                <button id="loginButton" type="submit" class="btn btn-default">Login</button>
                            </div>
                        </div>
                        <br>
                        <br>
                    </form>
                </div>
            </div>
        </div>
