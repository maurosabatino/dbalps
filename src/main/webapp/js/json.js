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

    autocompleti.push(new Dati('servletJson?op=processi', "#processo", "#idProcesso", 'idProcesso', 'nome', 'nome', 'nome'));
    autocompleti.push(new Dati('servletJson?op=litologia', "#nomeLitologia_IT", "#idLitologia", 'idLitologia', 'nomeLitologia_IT', 'nomeLitologia_IT', 'nomeLitologia_IT'));
    autocompleti.push(new Dati('servletJson?op=litologia', "#nomeLitologia_ENG", "#idLitologia", 'idLitologia', 'nomeLitologia_ENG', 'nomeLitologia_ENG', 'nomeLitologia_ENG'));
    autocompleti.push(new Dati('servletJson?op=statoFratturazione', "#statoFratturazione_IT", "#idStatoFratturazione", 'idStatoFratturazione', 'statoFratturazione_IT', 'statoFratturazione_IT', 'statoFratturazione_IT'));
    autocompleti.push(new Dati('servletJson?op=statoFratturazione', "#statoFratturazione_ENG", "#idStatoFratturazione", 'idStatoFratturazione', 'statoFratturazione_ENG', 'statoFratturazione_ENG', 'statoFratturazione_ENG'));
    autocompleti.push(new Dati('servletJson?op=proprietaTermiche', "#proprietaTermiche_IT", "#idProprietaTermiche", 'idProprietaTermiche', 'proprietaTermiche_IT', 'proprietaTermiche_IT', 'proprietaTermiche_IT'));
    autocompleti.push(new Dati('servletJson?op=proprietaTermiche', "#proprietaTermiche_ENG", "#idProprietaTermiche", 'idProprietaTermiche', 'proprietaTermiche_ENG', 'proprietaTermiche_ENG', 'proprietaTermiche_ENG'));
    autocompleti.push(new Dati('servletJson?op=sitoProcesso', "#caratteristicaSito_IT", "#idsito", 'idSito', 'caratteristicaSito_IT', 'caratteristicaSito_IT', 'caratteristicaSito_IT'));
    autocompleti.push(new Dati('servletJson?op=sitoProcesso', "#caratteristicaSito_ENG", "#idsito", 'idSito', 'caratteristicaSito_ENG', 'caratteristicaSito_ENG', 'caratteristicaSito_ENG'));
    autocompleti.push(new Dati('servletJson?op=classeVolume', "#intervallo", "#idclasseVolume", 'idClasseVolume', 'intervallo', 'intervallo', 'intervallo'));
    autocompleti.push(new Dati('servletJson?op=sitoStazione', "#caratteristiche_IT", "#idsitostazione", 'idSitoStazioneMetereologica', 'caratteristiche_IT', 'caratteristiche_IT', 'caratteristiche_IT'));
    autocompleti.push(new Dati('servletJson?op=sitoStazione', "#caratteristiche_ENG", "#idsitostazione", 'idSitoStazioneMetereologica', 'caratteristiche_ENG', 'caratteristiche_ENG', 'caratteristiche_ENG'));
    autocompleti.push(new Dati('servletJson?op=ente', "#ente", "#idEnte", 'idEnte', 'ente', 'ente', 'ente'));
    
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