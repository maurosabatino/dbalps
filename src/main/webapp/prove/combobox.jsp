
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Demo: Dijit ComboBox</title>
        <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/dojo/1.7.6/dijit/themes/claro/claro.css" media="screen">
        <jsp:useBean id="json" class="it.cnr.to.geoclimalp.dbalps.bean.json" scope="request" />
<jsp:setProperty  name="json" property="*"/>
    </head>
    <body>
        <div id="stateSelect"></div>
        <input type="text" id="stato"/> 
        <input type="text" id="id"/> 



        <!-- load dojo and provide config via data attribute -->
        <script src="//ajax.googleapis.com/ajax/libs/dojo/1.7.6/dojo/dojo.js" data-dojo-config="isDebug: true, async: true"></script>
        <script>
            require(['dojo/data/ItemFileReadStore', 'dijit/form/ComboBox'], function (ItemFileReadStore, ComboBox) {
                var store = new ItemFileReadStore({
                    data: {
                        identifier: "label",
                        items: ${json.locazioneAmministrativa}
                    }
                });
                var comboBox = new ComboBox({
                    id: "stateSelect",
                    name: "state",
                    store: store,
                    searchAttr: "label"
                }, "stateSelect");


                comboBox.on("Change", function (value) {
                    var id = store.getValue(comboBox.item, "idComune");
                    var name = store.getValue(comboBox.item, "label");
                    document.getElementById("stato").value = name;
                    document.getElementById("id").value = id;
                });
            });
        </script>
    </body>

</html>
