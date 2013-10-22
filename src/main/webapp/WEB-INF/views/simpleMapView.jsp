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
    <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=${obpConfig.googleApiKey}&sensor=true">
    </script>
    <script type="text/javascript">

        var mapLatitude = ${latitude};
        var mapLongitude = ${longitude};
        var mapMarker;

        $(document).ready(function() {
            setInterval("refreshMap()",3000);
            refreshMap();
        });

        function refreshMap() {
            $.ajax({
                type: "GET",
                url: "<c:url value="/api/gps/position"/>",
                data: "user=success",
                success: function(data) {
                    mapMarker.setPosition(new google.maps.LatLng(data.latitude,data.longitude));
                    mapMarker.setTitle("The Tower ("+data.latitude+" "+data.longitude);
                    mapMarker.setAnimation(google.maps.Animation.BOUNCE);
                    setTimeout(function() {mapMarker.setAnimation(null); }, 750);
                }
            });
        }

        function initialize() {
            var myPosition = new google.maps.LatLng(${latitude},${longitude});
            var mapOptions = {
                center: myPosition,
                zoom: 16,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

            mapMarker = new google.maps.Marker({
                position: myPosition,
                map: map,
                animation: google.maps.Animation.DROP,
                title: "The Tower"
            });
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>
<body>
<obp:header headline="Position on Map" btnHome="true"/>
<div id="map-canvas"/>
</body>
</html>