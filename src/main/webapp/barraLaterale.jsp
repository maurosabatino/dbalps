
<%@page import="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua"%>
<%@page import="java.util.Locale"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Role"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.Utente"%>


<link rel="stylesheet" type="text/css" href="css/flag-icon.min.css"/>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
<div class="col-md-2 col-md-offset-1" id=login>
    
    <ul class="nav nav-sidebar">
        <li>
            <a>
                <span class="fa fa-home fa-2x" id="home"></span>
                <span class="flag-icon flag-icon-it" id="buttonIT"> </span>
                <span class = "flag-icon flag-icon-gb" id="buttonENG" ></span>
            </a>
        </li>
    </ul>
   

    <ul class="nav nav-sidebar">
        <% if(session.getAttribute("locale")==null){
        ControllerLingua locale = new ControllerLingua(Locale.forLanguageTag("en-US"));
            session.setAttribute("locale", locale);
    }
            Utente part = (Utente) session.getAttribute("partecipante");
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
        <%String path = System.getProperty("catalina.base") + "\\resources\\";%>
        <%if(session.getAttribute("loc")!=null && session.getAttribute("loc").equals("en-US")){%>
        <li><a href="Servlet?operazione=downloadAllegato&file=<%=path%>dbalps_note_en.pdf"> ${locale.getWord("documentazione")} </a></li>
        <%}else{%>
       <li><a href="Servlet?operazione=downloadAllegato&file=<%=path%>\dbalps_note_it.pdf"> ${locale.getWord("documentazione")} </a></li>
       <% }%>
        
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

            <form method="POST" id="login_form" action="Servlet" >
                <div class="form-group">
                    <br>
                    <br>
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1">
                            <label for="username">Username </label>
                            <input type="text" name="username" id="username" class="form-control" placeholder="username"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-10 col-md-offset-1">
                            <label for="password">Password </label>
                            <input type="password" name="password" id = "password" class="form-control" placeholder="password"/>
                        </div>
                    </div>
                    <div class="row">
                        <div  class="col-md-10 col-md-offset-1">
                            <p id="msgbox"></p>
                        </div>
                    </div>
                </div>



                <div class="row">
                    <div class="col-md-10 col-md-offset-4">
                        <button id="loginButton" class="btn btn-default">Login</button>
                    </div>
                </div>
                <br>
                <br>

            </form>
        </div>
    </div>
</div>
<script>
    $("#login_form").submit(function () {
            //remove previous class and add new "myinfo" class
            $("#msgbox").removeClass().addClass('text-info').text('Validating Your Login ').fadeIn(1000);

            this.timer = setTimeout(function () {
                $.ajax({
                    url: 'Servlet',
                    data: 'operazione=login&username=' + $('#username').val() + '&password=' + $('#password').val(),
                    type: 'post',
                    success: function (msg) {
                        if (msg === 'ok') {
                            $("#msgbox").html('Login Verified, Logging in.....').addClass('text-success').fadeTo(900, 1,
                                    function () {
                                        location.reload();
                                    });

                        } else {
                            $("#msgbox").fadeTo(200, 0.1,
                                    function () {
                                        $(this).html('Sorry, Wrong Combination Of Username And Password.').removeClass().addClass('text-danger').fadeTo(900, 1);
                                    });
                        }
                    }
                });
            }, 200);
            return false;
        });
   
        

        $("#buttonIT").click(function () {
            $.ajax({
                url: 'Servlet',
                type: 'POST',
                data: {operazione: 'changeLanguage', loc: 'it-IT'},
                success: function () {
                    window.location.reload();
                }
            });
        });
        $("#buttonENG").click(function () {
            $.ajax({
                url: 'Servlet',
                type: 'POST',
                data: {operazione: 'changeLanguage', loc: 'en-US'},
                success: function () {
                    window.location.reload();
                }
            });
        });

        $("#home").click(function () {
            window.location = 'index.jsp';
        });

        
        $("#logout").click(function () {
            $.ajax({
                url: 'Servlet',
                type: 'POST',
                data: {operazione: 'logout'},
                success: function () {
                    window.location = 'index.jsp';
                }
            });
        });

  
</script>