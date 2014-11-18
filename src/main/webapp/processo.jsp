<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >

<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>
<html>

    <head>
        <!--CSS-->
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
        <link rel="stylesheet" type="text/css" href="css/layout.css"/>
        <link rel="stylesheet" type="text/css" href="css/bootstrapValidator.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.4.custom.css"/>

        <!--JAVASCRIPT-->
        <script src="js/jquery-2.1.1.min.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/globalize.js"></script>
        <script src="js/globalize.culture.de-DE.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/SeparateDate.js"></script>
        <script src="js/personalLibrary.js"></script>
        <script src="js/bootstrapValidator.min.js"></script>
        <script src="js/jquery.sticky-kit.min.js"></script>
        <script src="js/jquery.stickyfooter.min.js"></script>

        <!--Google Maps-->
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
        <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>

        <title>Processo</title>
    </head>
    <body>
        <div class ="container">
            <jsp:include page="header.jsp"></jsp:include>

                <div class="row">
                <jsp:include page="barraLaterale.jsp"></jsp:include>
                    <div class="col-md-8">
                    <jsp:getProperty name="HTMLc" property="content"/>
                    </div>
                </div>



            <jsp:include page="footer.jsp"></jsp:include>
        </div>

    </body>
</html>