<%-- 
    Document   : visualizzaStazione
    Created on : 12-nov-2014, 9.33.31
    Author     : Mauro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="stazione" class="it.cnr.to.geoclimalp.dbalps.bean.stazione.StazioneMetereologica" scope="request" />
<jsp:setProperty  name="stazione" property="*"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report Stazione</title>
    </head>
    <body>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="row">
                    <div class="col-md-9 col-md-push-2"><h1><jsp:getProperty name="stazione" property="nome "/></h1></div>
                    <div class="col-md-2 col-md-pull-9"><h1>Stazione</h1> </div>
                </div>
            </div>
        </div>
    </body>
</html>
