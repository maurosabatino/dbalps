<%@page import="java.util.Calendar"%>
<%@page import="java.lang.Object"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.attributiProcesso.TipologiaProcesso"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.processo.Processo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.cnr.to.geoclimalp.dbalps.bean.Utente.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >



<html>

    <head>
  
        <jsp:include page="import.jsp"></jsp:include>
              <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>
<script src="js/jquery.tablesorter.widgets.js"></script>
<link rel="stylesheet" type="text/css" href="css/theme.bootstrap.css"/>
            <script>
                $(document).ready(function () {

                   $(function() {

	// NOTE: $.tablesorter.theme.bootstrap is ALREADY INCLUDED in the jquery.tablesorter.widgets.js
	// file; it is included here to show how you can modify the default classes
	$.tablesorter.themes.bootstrap = {
		// these classes are added to the table. To see other table classes available,
		// look here: http://getbootstrap.com/css/#tables
		table      : 'table table-bordered',
		caption    : 'caption',
		header     : 'bootstrap-header', // give the header a gradient background
		footerRow  : '',
		footerCells: '',
		icons      : '', // add "icon-white" to make them white; this icon class is added to the <i> in the header
		sortNone   : 'bootstrap-icon-unsorted',
		sortAsc    : 'icon-chevron-up glyphicon glyphicon-chevron-up',     // includes classes for Bootstrap v2 & v3
		sortDesc   : 'icon-chevron-down glyphicon glyphicon-chevron-down', // includes classes for Bootstrap v2 & v3
		active     : '', // applied when column is sorted
		hover      : '', // use custom css here - bootstrap class may not override it
		filterRow  : '', // filter row class
		even       : '', // odd row zebra striping
		odd        : ''  // even row zebra striping
	};

	// call the tablesorter plugin and apply the uitheme widget
	$("table").tablesorter({
		// this will apply the bootstrap theme if "uitheme" widget is included
		// the widgetOptions.uitheme is no longer required to be set
		theme : "bootstrap",

		widthFixed: true,

		headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!

		// widget code contained in the jquery.tablesorter.widgets.js file
		// use the zebra stripe widget if you plan on hiding any rows (filter widget)
		widgets : [ "uitheme", "filter", "zebra" ],

		widgetOptions : {
			// using the default zebra striping class name, so it actually isn't included in the theme variable above
			// this is ONLY needed for bootstrap theming if you are using the filter widget, because rows are hidden
			zebra : ["even", "odd"],

			// reset filters button
			filter_reset : ".reset"

			// set the uitheme widget to use the bootstrap theme class names
			// this is no longer required, if theme is set
			// ,uitheme : "bootstrap"

		}
	})
	.tablesorterPager({

		// target the pager markup - see the HTML block below
		container: $("#pager"),

		// target the pager page select dropdown - choose a page
		cssGoto  : ".pagenum",

		// remove rows from the table to speed up the sort of large tables.
		// setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
		removeRows: true,

		// output string - default is '{page}/{totalPages}';
		// possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
		output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'

	});

});

                    function elimina(arg) {
                        var domanda = confirm("Sei sicuro di voler cancellare?");
                        if (domanda === true) {
                            $.ajax({
                                url: 'Servlet',
                                type: 'POST',
                                data: {operazione: 'eliminaProcesso', idProcesso: arg},
                                success: function () {
                                    window.location.reload();
                                }
                            });
                        }
                    }
                    ;
                });
            </script> 


            <title>Visualizza processi</title>
        </head>
        <body>
            <div class ="container">
            <jsp:include page="header.jsp"></jsp:include>
                <div class="row">
                <jsp:include page="barraLaterale.jsp"></jsp:include>


                    <div class="col-md-8"> 
                        <table class="tablesorter">
                            <thead>
                                <tr> 
                                    <th>Nome</th>
                                    <th>Data</th>
                                    <th>Ora</th> 
                                    <th>Comune</th>
                                    <th>Tipologia</th>
                                    <th> Dettagli</th>
                                    <th> Modifica</th>
                                    <th> Elimina</th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr> 
                                    <th>Nome</th>
                                    <th>Data</th>
                                    <th>Ora</th> 
                                    <th>Comune</th>
                                    <th>Tipologia</th>
                                    <th> Dettagli</th>
                                    <th> Modifica</th>
                                    <th> Elimina</th>
                                </tr>
                                <tr>
                                    <th id="pager" colspan="8" class="form-horizontal">
                                        <button type="button" class="btn first"><i class="icon-step-backward glyphicon glyphicon-step-backward"></i></button>
                                        <button type="button" class="btn prev"><i class="icon-arrow-left glyphicon glyphicon-backward"></i></button>
                                        <input type="text" class="pagedisplay"/> <!-- this can be any element, including an input -->
                                        <button type="button" class="btn next"><i class="icon-arrow-right glyphicon glyphicon-forward"></i></button>
                                        <button type="button" class="btn last"><i class="icon-step-forward glyphicon glyphicon-step-forward"></i></button>
                                        <select class="pagesize input-mini" title="Select page size">
                                            <option selected="selected" value="10">10</option>
                                            <option value="20">20</option>
                                            <option value="30">30</option>
                                            <option value="40">40</option>
                                        </select>
                                    </th>
                                </tr>
                            </tfoot>
                            <tbody>
                            <%
                                ArrayList<Processo> processo = (ArrayList<Processo>) request.getAttribute("processo");
                                for (Processo p : processo) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTimeInMillis(p.getData().getTime());%>

                            <tr> 
                                <td><%=p.getNome()%> </td>
                                <td><%="" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH)%></td>
                                <td><%="" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)%></td> 
                                <td><%= p.getUbicazione().getLocAmm().getComune()%> </td>
                                <% int i = 0;
                                    StringBuilder tipologia = new StringBuilder();
                                    for (TipologiaProcesso t : p.getAttributiProcesso().getTipologiaProcesso()) {
                                        if (!tipologia.toString().equals("")) {
                                            tipologia.append(" ,");
                                        }
                                        tipologia.append(t.getNome_IT());
                                    }
                                %>
                                <td><%=tipologia.toString()%></td>
                                <td> <a href="Servlet?operazione=mostraProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button"><img alt="Brand" class="img-responsive" src="img/search-icon (32).png"></a></td>
                                        <% Utente part = (Utente) session.getAttribute("partecipante");
                                            if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td> <a href="Servlet?operazione=mostraModificaProcesso&idProcesso=<%=p.getIdProcesso()%>" role="button" ><img alt="Brand" class="img-responsive" src="img/edit-validated-icon.png"></a></td>
                                        <%}%>
                                        <%
                                            if (part != null && (part.getRuolo().equals(Role.AMMINISTRATORE) || part.getRuolo().equals(Role.AVANZATO) || (part.getRuolo().equals(Role.BASE) && p.getAttributiProcesso().getIdUtente() == part.getIdUtente()))) {%>
                                <td> 
                                    <a id="buttonElimina" onclick="elimina(<%=p.getIdProcesso()%>)" role="button">
                                        <img alt="Brand" class="img-responsive" src="img/delete-icon.png">
                                    </a></td>
                                    <%}%>
                            </tr>
                            <%}%>   

                        </tbody>
                    </table>
                </div>
            </div>
            <jsp:include page="footer.jsp"></jsp:include>
        </div>

    </body>
</html>