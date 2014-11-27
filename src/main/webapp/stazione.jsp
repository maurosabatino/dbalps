<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>

<html>

    <head>
        <jsp:include page="import.jsp"></jsp:include>
    </head>
    <body>
        <div class="container">
            <jsp:include page="header.jsp"></jsp:include>
                <div class="row"> 
                <jsp:include page="barraLaterale.jsp"></jsp:include>

                    <div class="col-sm-10 col-md-8">
                    <jsp:getProperty name="HTMLc" property="content"/>
                </div>
            </div>
        </div>


    </body>
</html>