<%--
  Created by Robert Jaremczak
  Date: 2014-05-26
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp"%>
<!DOCTYPE html>
<html>
<head>
    <title>Map view</title>
  	<meta charset="utf-8" />
    <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
    <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
    <script src="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js" type="text/javascript"></script>
    <style>

        body {
            padding: 0;
            margin: 0;
        }
        html, body, #map {
            height: 100%;
        }

        .info h4 {
            margin: 0 0 5px;
            color: #777;
        }

    </style>
    <script>

        var theMap;
        var mapLatitude = ${latitude};
        var mapLongitude = ${longitude};
        var mapMarker;
        var mapMyPath;
        var remoteMarkers = new Array();
        var remotePaths = new Array();
        var localName = "${localName}";
        var localDescription = "${localDescription}";

        $(document).ready(function() {
            initialize();
            setInterval("refreshMap()",5000);
            refreshMap();
        });

        function formatPosition(latitude,longitude) {
            return new Number(latitude).toFixed(2)+" "+new Number(longitude).toFixed(2);
        }

        function refreshMap() {
            $.ajax({
                type: "GET",
                url: "<c:url value="/simple/gps/position"/>",
                data: "user=success",
                success: function(data) {
                    mapMarker.setLatLng([data.latitude,data.longitude]);
                    mapMarker._icon.title = "name: "+localName+"\ndescription: "+localDescription+"\nposition: "+formatPosition(data.latitude,data.longitude);
                    mapMarker.update();
                }
            });

            $.ajax({
                type: "GET",
                url: "<c:url value="/local/ownPath"/>",
                data: "user=success",
                success: function(data) {
                    mapMyPath.setLatLngs(convertToPath(data));
                }
            });

            $.ajax({
                type: "GET",
                url: "<c:url value="/local/otherRoutes"/>",
                data: "user=success",
                success: function(data) {
                    for(var i=0; i<remoteMarkers.length; i++) {
                        theMap.removeLayer(remoteMarkers[i]);
                    }

                    for(var i=0; i<remotePaths.length; i++) {
                        theMap.removeLayer(remotePaths[i]);
                    }

                    remoteMarkers = new Array();
                    remotePaths = new Array();

                    for(var i=0; i<data.length; i++) {
                        var position = data[i].position;
                        var marker = L.marker([position.latitude,position.longitude], {
                            title: "name: "+data[i].body.name+"\ndescription: "+data[i].body.description+"\nposition: "+formatPosition(position.latitude,position.longitude),
                            opacity: 0.5
                        });

                        theMap.addLayer(marker);
                        remoteMarkers.push(marker);

                        var path = L.polyline(convertToPath(data[i].path), {
                            color: 'blue',
                            weight: 2,
                            opacity: 0.5
                        });
                        theMap.addLayer(path);
                        remotePaths.push(path);
                    }
                }
            })
        }

        function newRoute() {
            $.ajax({
                type: "GET",
                url: "<c:url value="/local/ownPath?randomize=true"/>",
                data: "user=success",
                success: function(data) {
                    mapMyPath.setLatLngs(convertToPath(data));
                }
            });
        }

        function convertToPath(data) {
            var path = new Array();
            for(var i=0; i<data.length; i++) {
                path[i] = [data[i].latitude,data[i].longitude];
            }
            return path;
        }

        function initialize() {
            theMap = new L.Map('map', {
                minZoom: 2,
                maxZoom: 20,
                crs: L.CRS.EPSG3857
            }).setView([${latitude},${longitude}], 16);

            L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
                attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            }).addTo(theMap);

            var enctransparentlandlayer = new L.TileLayer.WMS("http://services.ecc.no/wms/wms_ip/marsec", {
                layers: 'cells',
                format: 'image/png',
                styles: 'style-id-2142',
                attribution: '<a href="http://primar.org/">PRIMAR</a>',
                transparent: true
            }).addTo(theMap);

            L.control.scale({imperial: false, maxWidth: 200}).addTo(theMap);

            var enclayers = [enctransparentlandlayer];
            var START_DEPTH = 10;
            setDepth(START_DEPTH);

            function setDepth(depth) {
                for (var i = 0; i < enclayers.length; i++) {
                    enclayers[i].setParams({ SAFETY_DEPTH: depth, SAFETY_CONTOUR: depth, DEEP_CONTOUR: depth });
                }
            }

            function depthSelectOnChange(sel) {
                var depth = sel.options[sel.selectedIndex].value;
                setDepth(depth);
            }

            var depthControl = L.control({position: 'bottomright'});
            depthControl.onAdd = function (theMap) {

                var div = L.DomUtil.create('div', 'info');
                L.DomEvent.disableClickPropagation(div);

                var select = "ENC Depth: <select id=\"depthselect\" onchange=\"depthSelectOnChange(this);\">";

                var depth = 2;
                while (depth < 80) {
                    select += "<option value=\"" + depth + "\"";
                    if (depth == START_DEPTH) {
                        select += " selected ";
                    }
                    select += ">" + depth + "m</option>"

                    if (depth < 10) depth = depth + 2;
                    else if (depth < 30) depth = depth + 5;
                    else depth = depth + 10;
                }

                select += "</select>";
                div.innerHTML = select;

                return div;
            };
            depthControl.addTo(theMap);

            mapMarker = L.marker([mapLatitude,mapLongitude], {
                title: "The Tower"
            });
            theMap.addLayer(mapMarker);

            mapMyPath = L.polyline([], {
                color: 'blue',
                weight: 2,
                opacity: 1.0
            });
            theMap.addLayer(mapMyPath);
        }
    </script>
</head>
<body>
    <div style="text-align: center">
        <div style="display: inline-block; width: 100px">
            <div class="shortButton" onclick="location.href='<c:url value="/simple/view"/>'">back</div>
        </div>
        <div style="display: inline-block; width: 120px">
            <div class="shortButton" onclick="location.href='<c:url value="/simple/position"/>'">details</div>
        </div>
        <div style="display: inline-block; width: 140px">
            <div class="shortButton" onclick="newRoute()">new route</div>
        </div>
    </div>
    <div style="height: 93%" id="map"></div>
</body>
</html>