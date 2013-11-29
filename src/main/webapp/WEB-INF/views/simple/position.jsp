<%--
  Created by Robert Jaremczak
  Date: 10/2/13
  Time: 1:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp" %>

<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
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
</head>
<body style="text-align: left">
<div id="backButton" style="display: inline-block; width: 300px; text-align: center; font-size: 1em">
    <div class="shortButton" onclick="location.href='<c:url value="/simple/map"/>'">back</div>
</div>
<table id="dataList" class="dataList" style="font-size: small">
    <tr>
        <td>Fix time:</td>
        <td><span id="fixTime">n/a</span> UTC</td>
    </tr>
    <tr>
        <td>Fix quality:</td>
        <td><span id="fixQuality">n/a</span></td>
    </tr>
    <tr>
        <td>Fix mode:</td>
        <td><span id="fixMode">n/a</span></td>
    </tr>
    <tr>
        <td>Fix type:</td>
        <td><span id="fixType">n/a</span></td>
    </tr>
    <tr>
        <td>Latitude:</td>
        <td><span id="latitude">n/a</span> &deg;</td>
    </tr>
    <tr>
        <td>Longitude:</td>
        <td><span id="longitude">n/a</span> &deg;</td>
    </tr>
    <tr>
        <td>True North course:</td>
        <td><span id="trueNorthCourse">n/a</span> &deg;</td>
    </tr>
    <tr>
        <td>Altitude:</td>
        <td><span id="altitude">n/a</span> m</td>
    </tr>
    <tr>
        <td>Speed over ground:</td>
        <td><span id="speedOverGround">n/a</span> m/s</td>
    </tr>
    <tr>
        <td>Magnetic variation:</td>
        <td><span id="magneticVariation">n/a</span> &deg;</td>
    </tr>
    <tr>
        <td>Position DOP:</td>
        <td><span id="pdop">n/a</span></td>
    </tr>
    <tr>
        <td>Horizontal DOP:</td>
        <td><span id="hdop">n/a</span></td>
    </tr>
    <tr>
        <td>Vertical DOP:</td>
        <td><span id="vdop">n/a</span></td>
    </tr>
</table>
<br>Satellites in view (effective <span id="effectiveSatellites"></span>):<br>

<div id="satellitesInView"></div>
<script type="text/javascript">

    $(function () {
        setInterval("ajaxd()", 3000);
        ajaxd();
        doLayout();
    });

    function doLayout() {
    }

    function ajaxd() {
        $.ajax({
            type: "GET",
            url: "<c:url value="/simple/gps/all"/>",
            data: "user=success",
            success: function (data) {
                $("#fixTime").fadeOut();
                $("#fixTime").text(data.dateTime);
                $("#fixTime").fadeIn();
                $("#latitude").text(data.latitude);
                $("#longitude").text(data.longitude);
                $("#trueNorthCourse").text(data.trueNorthCourse);
                $("#magneticVariation").text(data.gpsMagneticVariation);
                $("#speedOverGround").text(data.speedOverGround);
                $("#effectiveSatellites").text(data.gpsEffectiveSatellites);
                $("#altitude").text(data.altitude);
                $("#fixQuality").text(data.gpsFixQuality);
                $("#fixMode").text(data.gpsFixMode);
                $("#fixType").text(data.gpsFixType);
                $("#pdop").text(data.pdop);
                $("#hdop").text(data.hdop);
                $("#vdop").text(data.vdop);

                var html = "<table class='tabularData'>" +
                        "<tr>" +
                        "<th>#</th>" +
                        "<th>sat ID</th>" +
                        "<th>elevation<br>[&deg;]</th>" +
                        "<th>azimuth<br>[&deg;]</th>" +
                        "<th>s/n<br>[dbm]</th>" +
                        "</tr>";
                for (var i in data.gpsSatellites) {
                    var sat = data.gpsSatellites[i];
                    html += "<tr>";
                    html += "<td>" + (Number(i) + 1) + "</td>";
                    html += "<td>" + sat.id + "</td>";
                    html += "<td>" + sat.elevation + "</td>";
                    html += "<td>" + sat.azimuth + "</td>";
                    html += "<td>" + avoidNaN(sat.snr) + "</td>";
                    html += "</tr>";
                }
                html += "</table>"
                $("#satellitesInView").html(html);
            }
        });
    }

    function avoidNaN(num) {
        return isNaN(num) ? "n/a" : String(num);
    }
</script>
</body>
</html>