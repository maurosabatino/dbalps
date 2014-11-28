<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >

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


<!--JAVASCRIPT-->


<script src ="js/jquery-1.11.1.min.js"></script>
<script src ="js/jquery-ui.min.js"></script>
<script src="js/bootstrap.js"></script>

<script src="js/selectize.js"></script>







<!--Google Maps-->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD2ZrcNbP1btezQE5gYgeA7_1IY0J8odCQ&sensor=false"></script>
<script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js"></script>
       <script>
       $(document).ready(function () {
    function Dati(url, input, target, value, label, search, sort) {
        this.url = url;
        this.input = input;
        this.target = target;
        this.value = value;
        this.label = label;
        this.search = search;
        this.sort = sort;
    }
    var autocompleti = [];

    autocompleti.push(new Dati('servletJson?op=processi', "#nome", "#nome", 'nome', 'nome', 'nome', 'nome'));
        
    $.each(autocompleti, function (index, value) {
        /*prendo il json dalla response della url*/
        var json = (function () {
            var json = null;
            $.ajax({
                async: false,
                global: false,
                url: value.url,
                dataType: 'json',
                success: function (data) {
                    json = data;
                }
            });
            return json;
        })();

        /*chiamo la funzione selectize con option il json passato in precedenza*/
        $(value.input).selectize({
            valueField: value.value,
            labelField: value.label,
            searchField: value.search,
            sortField: value.sort,
            options: json,
            onChange: function () {
                $(value.target).val(this.getValue());
            }
        });
    });

    function locazioneAmministrativa() {
        /*prendo il json dalla response della url*/
        var json = (function () {
            var json = null;
            $.ajax({
                async: false,
                global: false,
                url: 'servletJson?op=locAmm',
                dataType: 'json',
                success: function (data) {
                    json = data;
                }
            });
            return json;
        })();

        /*chiamo la funzione selectize con option il json passato in precedenza*/
        $("#comune").selectize({
            valueField: 'idComune',
            labelField: 'comune',
            searchField: 'comune',
            sortField: 'comune',
            options: json,
            onChange: function () {
                $("#idcomune").val(this.getValue());
                var locAmm = [];
                var search = this.getValue();
                for (var i = 0; i < json.length; i++) {
                    if (json[i].idComune == search) {
                        locAmm[0] = json[i];
                    }
                }
                $("#provincia").val(locAmm[0].provincia);
                $("#regione").val(locAmm[0].regione);
                $("#nazione").val(locAmm[0].nazione);
            }
        });
    }
    ;
    function locazioneIdrologica() {
        /*prendo il json dalla response della url*/
        var json = (function () {
            var json = null;
            $.ajax({
                async: false,
                global: false,
                url: 'servletJson?op=locIdro',
                dataType: 'json',
                success: function (data) {
                    json = data;
                }
            });
            return json;
        })();

        /*chiamo la funzione selectize con option il json passato in precedenza*/
        $("#sottobacino").selectize({
            valueField: 'idSottobacino',
            labelField: 'sottobacino',
            searchField: 'sottobacino',
            sortField: 'sottobacino',
            options: json,
            onChange: function () {
                $("#idSottobacino").val(this.getValue());
                var locIdro = [];
                var search = this.getValue();
                for (var i = 0; i < json.length; i++) {
                    if (json[i].idSottobacino == search) {
                        locIdro[0] = json[i];
                    }
                }
                $("#bacino").val(locIdro[0].bacino);
            }
        });
    }
    ;
    locazioneAmministrativa();
    locazioneIdrologica();
});
</script>
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