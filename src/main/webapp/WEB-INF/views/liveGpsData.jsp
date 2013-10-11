<%--
  Created by Robert Jaremczak
  Date: 10/2/13
  Time: 1:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>OBP - Live GPS Data</title>
        <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
        <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    </head>
    <script type="text/javascript">
        $(document).ready(function() {
            setInterval("ajaxd()",3000);
        });

        function ajaxd() {
            $.ajax({
                type: "GET",
                url: "<c:url value="/api/gps/position"/>",
                data: "user=success",
                success: function(data){
                    $("#fixTime").fadeOut();
                    $("#fixTime").text(data.fixTime);
                    $("#fixTime").fadeIn();
                    $("#latitude").text(data.latitude);
                    $("#longitude").text(data.longitude);
                    $("#trueNorthHeading").text(data.trueNorthHeading);
                    $("#velocityOverGround").text(data.velocityOverGround);
                }
            });
        }    </script>
    <body>
        <h1>Live GPS Data</h1>
        <hr/>
        <span class="faded">OBP-${buildId}</span>
        <br/><br/><br/>
        <table>
            <tr>
                <td>Fixing time</td>
                <td><span class="liveData" id="fixTime">-</span> UTC</td>
            </tr>
            <tr>
                <td>Latitude</td>
                <td><span class="liveData" id="latitude">-</span> &deg;</td>
            </tr>
            <tr>
                <td>Longitude</td>
                <td><span class="liveData" id="longitude">-</span> &deg;</td>
            </tr>
            <tr>
                <td>True North heading</td>
                <td><span class="liveData" id="trueNorthHeading">-</span>  &deg;</td>
            </tr>
            <tr>
                <td>Velocity over ground</td>
                <td><span class="liveData" id="velocityOverGround">-</span> m/s</td>
            </tr>
        </table>
    </body>
</html>