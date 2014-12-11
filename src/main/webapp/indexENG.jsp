<%@page import="java.util.Locale"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.controller.ControllerLingua"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>
        <title>dbalps</title>
       <jsp:include page="import.jsp"></jsp:include>        
    </head>

    <body>
        
        <div class="container">
            <jsp:include page="header.jsp"></jsp:include>

                <div class="row container-fluid ">  
            <jsp:include page="barraLaterale.jsp"></jsp:include>
                    
                        <p id = "loginStatus"></p>
                        
                    <div class="col-md-8">
                    <%  ControllerLingua locale = (ControllerLingua)session.getAttribute("locale");    
                        if(locale.getLanguage().equals("it")){%>  
                 Benvenuto,

Questa è l'interfaccia web di dbalps, un database realizzato al fine di gestire dati relativi a processi di instabilità naturali che avvengono nell'ambiente alpino di alta quota e dati sui principali parametri climatici utili allo studio di tali processi.

Dbalps è stato sviluppato utilizzando applicativi free open source ed ispirandosi al concetto di riutilizzo del software pubblico. La consultazione di dbalps è gratuita.

Sei un escursionista, un rocciatore, una guida alpina, una guida del soccorso alpino, un guardia parco, oppure un gestore di rifugio?

Durante la tua attività in montagna, se sei venuto a conoscenza o hai assistito recentemente ad un crollo o ad una frana, per favore mandaci una segnalazione attraverso questo sito. Il tuo contributo ci aiuterà molto nello studio e nella comprensione di questi processi, grazie!
                         
<%}else{%>

    Welcome,

This is the integrated information management system web interface of the dbalps, a server-side relational database that is able to manage data about natural instability processes at high-elevation sites and the related information about the main climatic parameters that are necessary for the study of these processes.

The system has been developed using free open source applications. The consultation of the database is free of charge.

Are you a hiker, a rock climber, a mountain guide, a park guard, or a manager of an alpine hut? Are you working in a mountain rescue team?

If, during your mountain activities, you have seen a rockfall or a landslide, please sends a message by using this site.
Your contribution will help us to study these processes, many thanks!


    
<%}%>
                </div></div>
                <div class="row">
                <jsp:include page="footer.jsp"></jsp:include>
            </div>

        </div>
    </body>
</html>