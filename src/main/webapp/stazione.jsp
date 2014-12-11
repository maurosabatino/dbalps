<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<jsp:useBean id="HTMLc" class="it.cnr.to.geoclimalp.dbalps.bean.HTMLContent" scope="request" />
<jsp:setProperty  name="HTMLc" property="*"/>

<html>

    <head>
       <!--CSS-->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
<link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="css/selectize.bootstrap3.css"/>
<link rel="stylesheet" type="text/css" href="css/layout.css"/>
        <script src ="js/jquery-1.11.1.min.js"></script>
<script src ="js/jquery-ui.min.js"></script>
<script src="js/bootstrap.js"></script>

<script src="js/bootstrapValidator.min.js"></script>
<script src="js/validator.js"></script>
 <script src ="js/jquery.dataTables.min.js"></script>
            <script src ="js/dataTables.bootstrap.js"></script> 
<script>
                $(document).ready(function () {
                    
                    $('#tabella').dataTable({
                        "language": {
                        "lengthMenu": "Display _MENU_ process per page",
                        "zeroRecords": "Nothing found - sorry",
                        "info": "Showing page _PAGE_ of _PAGES_ ",
                        "infoEmpty": "No process available",
                        "infoFiltered": "(filtered from _MAX_ total process)"
                    }
                    });
                });
                    
   
            </script>     
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