
var map;
var coords = new Object();
coords.lat = 45.57560020947792;
coords.lng = 9.613037109375;
$(document).ready(function () {
    $("#map_container").dialog({
        autoOpen: false,
        width: '800',
        height: '600',
        position: "right center",
        resizeStop: function (event, ui) {
            google.maps.event.trigger(map, 'resize');
        },
        open: function (event, ui) {
            google.maps.event.trigger(map, 'resize');
        },
        buttons: {
            "conferma": function () {
                document.getElementById("latitudine").value = document.getElementById("lati").value;
                document.getElementById("longitudine").value = document.getElementById("long").value;
                $(this).dialog("close");
            }
        }
    });
    $("#showMap").click(function () {
        $("#map_container").dialog("open");
        map.setCenter(new google.maps.LatLng(coords.lat, coords.lng), 10);
        google.maps.event.addListener(map, "click", function (event) {
            var lat = event.latLng.lat();
            var lng = event.latLng.lng();
            document.getElementById("lati").value = lat;
            document.getElementById("long").value = lng;
        });
        return false;
    });
    $("input:submit,input:button, a, button", "#controls").button();
    initialize();
});
function initialize() {
    var latlng = new google.maps.LatLng(coords.lat, coords.lng);
    var myOptions = {
        zoom: 6,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.HYBRID
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
}
