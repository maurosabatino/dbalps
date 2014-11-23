
<%@page import="java.util.Locale"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua"%>
<div class="row">
    <div class="col-md-10 col-md-push-1">
        <div class ="header">
            <div class="navbar-default">
                <% ControllerLingua locale = (ControllerLingua)session.getAttribute("locale");
                if(locale == null) locale = new ControllerLingua(Locale.forLanguageTag("it-IT"));
                    if(locale.getLanguage().equals("it")){%>
                <img class="img-responsive" src="img/logo_ita.png">
                <%}else{%>
                <img class="img-responsive" src="img/logo_en.png">
                <%}%>
            </div>
        </div>
    </div>
</div>

