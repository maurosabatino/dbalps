




<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
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
        <jsp:useBean id="processo" class="it.cnr.to.geoclimalp.dbalps.bean.processo.ProcessoCompleto" scope="session" />
        <jsp:setProperty  name="processo" property="*"/>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       
    </body>
</html>
