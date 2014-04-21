<%--
  Created by Robert Jaremczak
  Date: 2013-10-22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
    <style>
        .shortButton {
            padding: 10px 20px;
            font-weight: normal;
            font-size: 1em;
            border: 2px solid white;
            border-radius: 20px;
            margin: 10px;
            color: white;
        }

        .shortButton:hover {
            background-color: #6f9dba;
            cursor: pointer;
        }
    </style>
    <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=${obpConfig.googleApiKey}&sensor=true">
    </script>
    <script type="text/javascript">

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
                    mapMarker.setPosition(new google.maps.LatLng(data.latitude,data.longitude));
                    mapMarker.setTitle("name: "+localName+"\ndescription: "+localDescription+"\nposition: "+formatPosition(data.latitude,data.longitude));
                    mapMarker.setAnimation(google.maps.Animation.BOUNCE);
                    setTimeout(function() {mapMarker.setAnimation(null); }, 750);
                }
            });

            $.ajax({
                type: "GET",
                url: "<c:url value="/local/ownPath"/>",
                data: "user=success",
                success: function(data) {
                    mapMyPath.setPath(convertToPath(data));
                }
            });

            $.ajax({
                type: "GET",
                url: "<c:url value="/local/otherRoutes"/>",
                data: "user=success",
                success: function(data) {
                    for(var i=0; i<remoteMarkers.length; i++) {
                        remoteMarkers[i].setMap(null);
                    }

                    for(var i=0; i<remotePaths.length; i++) {
                        remotePaths[i].setMap(null);
                    }

                    remoteMarkers = new Array();
                    remotePaths = new Array();

                    for(var i=0; i<data.length; i++) {
                        var position = data[i].position;
                        var marker = new google.maps.Marker({
                            position: new google.maps.LatLng(position.latitude,position.longitude),
                            map: theMap,
                            title: "name: "+data[i].body.name+"\ndescription: "+data[i].body.description+"\nposition: "+formatPosition(position.latitude,position.longitude),
                            icon: '../images/green-dot.png'
                        });

                        remoteMarkers.push(marker);

                        var path = new google.maps.Polyline({
                            path: convertToPath(data[i].path),
                            geodesic: true,
                            strokeColor: '#00FF00',
                            strokeOpacity: 1.0,
                            strokeWeight: 3
                        });

                        remotePaths.push(path);
                        path.setMap(theMap);
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
                    mapMyPath.setPath(convertToPath(data));
                }
            });
        }

        function convertToPath(data) {
            var path = new Array();
            for(var i=0; i<data.length; i++) {
                path[i] = new google.maps.LatLng(data[i].latitude,data[i].longitude);
            }
            return path;
        }

        function initialize() {
            var myPosition = new google.maps.LatLng(${latitude},${longitude});
            var mapOptions = {
                center: myPosition,
                zoom: 16,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            theMap = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

            mapMarker = new google.maps.Marker({
                position: myPosition,
                map: theMap,
                animation: google.maps.Animation.DROP,
                title: "The Tower"
            });

            mapMyPath = new google.maps.Polyline({
                path: new Array(),
                geodesic: true,
                strokeColor: '#FF0000',
                strokeOpacity: 1.0,
                strokeWeight: 3
            });

            mapMyPath.setMap(theMap);
        }

        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>
<body style="text-align: center">
<div style="display: inline-block; width: 100px">
    <div class="shortButton" onclick="location.href='<c:url value="/simple/view"/>'">back</div>
</div>
<div style="display: inline-block; width: 120px">
    <div class="shortButton" onclick="location.href='<c:url value="/simple/position"/>'">details</div>
</div>
<div style="display: inline-block; width: 140px">
    <div class="shortButton" onclick="newRoute()">new route</div>
</div>
<div style="height: 87%" id="map-canvas"/>
</body>
</html>