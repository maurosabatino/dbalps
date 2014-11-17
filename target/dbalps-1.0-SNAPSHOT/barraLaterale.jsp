<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Role"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente"%>


       
            <div class="col-md-2 col-md-offset-1" id=login>

                <ul class="nav nav-sidebar">
                    <li><a class="glyphicon glyphicon-home" href="index.jsp" ></a></li>
                </ul>

                <ul class="nav nav-sidebar">
                    <li><a class="img-circle" href="Servlet?operazione=changeLanguage&language=it-IT">
                            <img alt="Brand" src="img/italy_round_icon_64.png">Italiano
                        </a>
                        <a class="img-circle" href="Servlet?operazione=changeLanguage&language=en-US">
                            <img alt="Brand" src="img/united_kingdom_round_icon_64.png">English
                        </a>
                    </li>
                    <% Utente part = (Utente) session.getAttribute("partecipante");
                        if (part == null) {
                    %>	
                    <li><button class="btn btn-info navbar-btn " data-toggle="modal" data-target=".login-form">Login</button></li>
                    <%} else {%>
                    <li>
                        <form   action="Servlet" name="dati" method="POST">
                            <input type=hidden name=operazione value=logout>
                            Utente: <%=part.getUsername()%> <button type="submit" class="btn btn-warning navbar-btn btn-sm">Logout</button>
                        </form>
                    </li>
                    <% }%>
                </ul>
                
                <ul class="nav nav-sidebar">
                    <li class="dropdown">
                        <%if (part != null && ((part.getRuolo().equals(Role.AMMINISTRATORE)) || (part.getRuolo().equals(Role.AVANZATO)))) {%>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Inserisci <b class="caret"></b></a>
                            <%} else {%>
                        <a href="#" class="dropdown-toggle disabled" data-toggle="dropdown">Inserisci <b class="caret"></b></a>
                            <%}%>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=formInserisciProcesso"> inserisci processo</a></li>
                            <li><a href="Servlet?operazione=formInserisciStazione">Inserisci Stazione</a></li>
                            <li><a href="Servlet?operazione=elencocaricaDatiClimatici">Inserisci Dati Climatici</a></li>
                            <li><a href="Servlet?operazione=scegliProcessoAllegato">Allegato Processo</a></li>
                            <li><a href="Servlet?operazione=scegliStazioneAllegato">Allegato Stazione</a></li>

                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Mostra <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=mostraTuttiProcessiModifica">Mostra un Processo </a></li>
                            <li><a href="Servlet?operazione=mostraTutteStazioniMetereologiche"> mostra tutte le stazioni</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Ricerca <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=queryProcesso"> Ricerca Processo</a></li>
                            <li><a href="Servlet?operazione=queryStazione"> Ricerca Stazione Metereologica</a></li>
                            <li><a href="Servlet?operazione=queryClimatiche"> Climatiche</a></li>
                            <li><a href="Servlet?operazione=listaElaborazioni"> Elaborazioni</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <%if (part != null && part.getRuolo().equals(Role.AMMINISTRATORE)) {%>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Pannello Amministratore <b class="caret"></b></a>
                            <% } else { %>
                        <a href="#" class="dropdown-toggle disabled" data-toggle="dropdown">Pannello Amministratore <b class="caret"></b></a>
                            <%}%>
                        <ul class="dropdown-menu">
                            <li><a href="Servlet?operazione=formCreaUtente"> Crea Utente</a></li>
                            <li><a href="Servlet?operazione=visualizzaTuttiUtenti">visualizza tutti gli utenti</a></li>
                            <li><a href="Servlet?operazione=ricaricaJson">ricarica gli autocomplete</a></li>
                        </ul>
                    </li>


                </ul>
                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#"> Documentazione </a></li>
                    <li><a href="#">Info</a></li>
                </ul>

                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#"> Applicazione Android</a></li>
                </ul>

                <div class="divider"></div>
                <ul class="nav nav-sidebar">
                    <li><a href="#">Statistiche</a></li>
                </ul>
            </div>
                        
        <div class="modal login-form" tabindex="-6" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form action="Servlet" name="dati" method="POST">
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
                                <button type="submit" class="btn btn-default">Login</button>
                            </div>
                        </div>
                        <br>
                        <br>
                    </form>
                </div>
            </div>
        </div>
